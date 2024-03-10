/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Pedido;
import modelo.ReintegracaoAcademica;

/**
 *
 * @author Meldo Maunze
 */
public class ReintegracaoAcademicaJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public ReintegracaoAcademicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     *
     * @return
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     *
     * @param reintegracaoAcademica
     */
    public void create(ReintegracaoAcademica reintegracaoAcademica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = reintegracaoAcademica.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                reintegracaoAcademica.setPedido(pedido);
            }
            em.persist(reintegracaoAcademica);
            if (pedido != null) {
                pedido.getReintegracaoAcademicaList().add(reintegracaoAcademica);
                pedido = em.merge(pedido);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @param reintegracaoAcademica
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(ReintegracaoAcademica reintegracaoAcademica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReintegracaoAcademica persistentReintegracaoAcademica = em.find(ReintegracaoAcademica.class, reintegracaoAcademica.getId());
            Pedido pedidoOld = persistentReintegracaoAcademica.getPedido();
            Pedido pedidoNew = reintegracaoAcademica.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                reintegracaoAcademica.setPedido(pedidoNew);
            }
            reintegracaoAcademica = em.merge(reintegracaoAcademica);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getReintegracaoAcademicaList().remove(reintegracaoAcademica);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getReintegracaoAcademicaList().add(reintegracaoAcademica);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = reintegracaoAcademica.getId();
                if (findReintegracaoAcademica(id) == null) {
                    throw new NonexistentEntityException("The reintegracaoAcademica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReintegracaoAcademica reintegracaoAcademica;
            try {
                reintegracaoAcademica = em.getReference(ReintegracaoAcademica.class, id);
                reintegracaoAcademica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reintegracaoAcademica with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = reintegracaoAcademica.getPedido();
            if (pedido != null) {
                pedido.getReintegracaoAcademicaList().remove(reintegracaoAcademica);
                pedido = em.merge(pedido);
            }
            em.remove(reintegracaoAcademica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @return
     */
    public List<ReintegracaoAcademica> findReintegracaoAcademicaEntities() {
        return findReintegracaoAcademicaEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<ReintegracaoAcademica> findReintegracaoAcademicaEntities(int maxResults, int firstResult) {
        return findReintegracaoAcademicaEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<ReintegracaoAcademica> findReintegracaoAcademicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReintegracaoAcademica.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public ReintegracaoAcademica findReintegracaoAcademica(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReintegracaoAcademica.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getReintegracaoAcademicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReintegracaoAcademica> rt = cq.from(ReintegracaoAcademica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

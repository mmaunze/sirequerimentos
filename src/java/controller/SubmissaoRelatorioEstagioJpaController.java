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
import modelo.SubmissaoRelatorioEstagio;

/**
 *
 * @author Meldo Maunze
 */
public class SubmissaoRelatorioEstagioJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public SubmissaoRelatorioEstagioJpaController(EntityManagerFactory emf) {
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
     * @param submissaoRelatorioEstagio
     */
    public void create(SubmissaoRelatorioEstagio submissaoRelatorioEstagio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = submissaoRelatorioEstagio.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                submissaoRelatorioEstagio.setPedido(pedido);
            }
            em.persist(submissaoRelatorioEstagio);
            if (pedido != null) {
                pedido.getSubmissaoRelatorioEstagioList().add(submissaoRelatorioEstagio);
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
     * @param submissaoRelatorioEstagio
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(SubmissaoRelatorioEstagio submissaoRelatorioEstagio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubmissaoRelatorioEstagio persistentSubmissaoRelatorioEstagio = em.find(SubmissaoRelatorioEstagio.class, submissaoRelatorioEstagio.getId());
            Pedido pedidoOld = persistentSubmissaoRelatorioEstagio.getPedido();
            Pedido pedidoNew = submissaoRelatorioEstagio.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                submissaoRelatorioEstagio.setPedido(pedidoNew);
            }
            submissaoRelatorioEstagio = em.merge(submissaoRelatorioEstagio);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getSubmissaoRelatorioEstagioList().remove(submissaoRelatorioEstagio);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getSubmissaoRelatorioEstagioList().add(submissaoRelatorioEstagio);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = submissaoRelatorioEstagio.getId();
                if (findSubmissaoRelatorioEstagio(id) == null) {
                    throw new NonexistentEntityException("The submissaoRelatorioEstagio with id " + id + " no longer exists.");
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
            SubmissaoRelatorioEstagio submissaoRelatorioEstagio;
            try {
                submissaoRelatorioEstagio = em.getReference(SubmissaoRelatorioEstagio.class, id);
                submissaoRelatorioEstagio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The submissaoRelatorioEstagio with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = submissaoRelatorioEstagio.getPedido();
            if (pedido != null) {
                pedido.getSubmissaoRelatorioEstagioList().remove(submissaoRelatorioEstagio);
                pedido = em.merge(pedido);
            }
            em.remove(submissaoRelatorioEstagio);
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
    public List<SubmissaoRelatorioEstagio> findSubmissaoRelatorioEstagioEntities() {
        return findSubmissaoRelatorioEstagioEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<SubmissaoRelatorioEstagio> findSubmissaoRelatorioEstagioEntities(int maxResults, int firstResult) {
        return findSubmissaoRelatorioEstagioEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<SubmissaoRelatorioEstagio> findSubmissaoRelatorioEstagioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubmissaoRelatorioEstagio.class));
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
    public SubmissaoRelatorioEstagio findSubmissaoRelatorioEstagio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubmissaoRelatorioEstagio.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getSubmissaoRelatorioEstagioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubmissaoRelatorioEstagio> rt = cq.from(SubmissaoRelatorioEstagio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

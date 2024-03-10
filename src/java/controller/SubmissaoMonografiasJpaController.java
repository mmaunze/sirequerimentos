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
import modelo.SubmissaoMonografias;

/**
 *
 * @author Meldo Maunze
 */
public class SubmissaoMonografiasJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public SubmissaoMonografiasJpaController(EntityManagerFactory emf) {
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
     * @param submissaoMonografias
     */
    public void create(SubmissaoMonografias submissaoMonografias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = submissaoMonografias.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                submissaoMonografias.setPedido(pedido);
            }
            em.persist(submissaoMonografias);
            if (pedido != null) {
                pedido.getSubmissaoMonografiasList().add(submissaoMonografias);
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
     * @param submissaoMonografias
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(SubmissaoMonografias submissaoMonografias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubmissaoMonografias persistentSubmissaoMonografias = em.find(SubmissaoMonografias.class, submissaoMonografias.getId());
            Pedido pedidoOld = persistentSubmissaoMonografias.getPedido();
            Pedido pedidoNew = submissaoMonografias.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                submissaoMonografias.setPedido(pedidoNew);
            }
            submissaoMonografias = em.merge(submissaoMonografias);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getSubmissaoMonografiasList().remove(submissaoMonografias);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getSubmissaoMonografiasList().add(submissaoMonografias);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = submissaoMonografias.getId();
                if (findSubmissaoMonografias(id) == null) {
                    throw new NonexistentEntityException("The submissaoMonografias with id " + id + " no longer exists.");
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
            SubmissaoMonografias submissaoMonografias;
            try {
                submissaoMonografias = em.getReference(SubmissaoMonografias.class, id);
                submissaoMonografias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The submissaoMonografias with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = submissaoMonografias.getPedido();
            if (pedido != null) {
                pedido.getSubmissaoMonografiasList().remove(submissaoMonografias);
                pedido = em.merge(pedido);
            }
            em.remove(submissaoMonografias);
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
    public List<SubmissaoMonografias> findSubmissaoMonografiasEntities() {
        return findSubmissaoMonografiasEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<SubmissaoMonografias> findSubmissaoMonografiasEntities(int maxResults, int firstResult) {
        return findSubmissaoMonografiasEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<SubmissaoMonografias> findSubmissaoMonografiasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubmissaoMonografias.class));
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
    public SubmissaoMonografias findSubmissaoMonografias(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubmissaoMonografias.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getSubmissaoMonografiasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubmissaoMonografias> rt = cq.from(SubmissaoMonografias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

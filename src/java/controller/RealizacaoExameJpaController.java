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
import modelo.RealizacaoExame;

/**
 *
 * @author Meldo Maunze
 */
public class RealizacaoExameJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public RealizacaoExameJpaController(EntityManagerFactory emf) {
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
     * @param realizacaoExame
     */
    public void create(RealizacaoExame realizacaoExame) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = realizacaoExame.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                realizacaoExame.setPedido(pedido);
            }
            em.persist(realizacaoExame);
            if (pedido != null) {
                pedido.getRealizacaoExameList().add(realizacaoExame);
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
     * @param realizacaoExame
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(RealizacaoExame realizacaoExame) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RealizacaoExame persistentRealizacaoExame = em.find(RealizacaoExame.class, realizacaoExame.getId());
            Pedido pedidoOld = persistentRealizacaoExame.getPedido();
            Pedido pedidoNew = realizacaoExame.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                realizacaoExame.setPedido(pedidoNew);
            }
            realizacaoExame = em.merge(realizacaoExame);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getRealizacaoExameList().remove(realizacaoExame);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getRealizacaoExameList().add(realizacaoExame);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = realizacaoExame.getId();
                if (findRealizacaoExame(id) == null) {
                    throw new NonexistentEntityException("The realizacaoExame with id " + id + " no longer exists.");
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
            RealizacaoExame realizacaoExame;
            try {
                realizacaoExame = em.getReference(RealizacaoExame.class, id);
                realizacaoExame.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The realizacaoExame with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = realizacaoExame.getPedido();
            if (pedido != null) {
                pedido.getRealizacaoExameList().remove(realizacaoExame);
                pedido = em.merge(pedido);
            }
            em.remove(realizacaoExame);
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
    public List<RealizacaoExame> findRealizacaoExameEntities() {
        return findRealizacaoExameEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<RealizacaoExame> findRealizacaoExameEntities(int maxResults, int firstResult) {
        return findRealizacaoExameEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<RealizacaoExame> findRealizacaoExameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RealizacaoExame.class));
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
    public RealizacaoExame findRealizacaoExame(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RealizacaoExame.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getRealizacaoExameCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RealizacaoExame> rt = cq.from(RealizacaoExame.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Pedido;
import modelo.SituacaoDivida;

/**
 *
 * @author Meldo Maunze
 */
public class SituacaoDividaJpaController implements Serializable {

    public SituacaoDividaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SituacaoDivida situacaoDivida) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = situacaoDivida.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                situacaoDivida.setPedido(pedido);
            }
            em.persist(situacaoDivida);
            if (pedido != null) {
                pedido.getSituacaoDividaList().add(situacaoDivida);
                pedido = em.merge(pedido);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SituacaoDivida situacaoDivida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SituacaoDivida persistentSituacaoDivida = em.find(SituacaoDivida.class, situacaoDivida.getId());
            Pedido pedidoOld = persistentSituacaoDivida.getPedido();
            Pedido pedidoNew = situacaoDivida.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                situacaoDivida.setPedido(pedidoNew);
            }
            situacaoDivida = em.merge(situacaoDivida);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getSituacaoDividaList().remove(situacaoDivida);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getSituacaoDividaList().add(situacaoDivida);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = situacaoDivida.getId();
                if (findSituacaoDivida(id) == null) {
                    throw new NonexistentEntityException("The situacaoDivida with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SituacaoDivida situacaoDivida;
            try {
                situacaoDivida = em.getReference(SituacaoDivida.class, id);
                situacaoDivida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The situacaoDivida with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = situacaoDivida.getPedido();
            if (pedido != null) {
                pedido.getSituacaoDividaList().remove(situacaoDivida);
                pedido = em.merge(pedido);
            }
            em.remove(situacaoDivida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SituacaoDivida> findSituacaoDividaEntities() {
        return findSituacaoDividaEntities(true, -1, -1);
    }

    public List<SituacaoDivida> findSituacaoDividaEntities(int maxResults, int firstResult) {
        return findSituacaoDividaEntities(false, maxResults, firstResult);
    }

    private List<SituacaoDivida> findSituacaoDividaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SituacaoDivida.class));
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

    public SituacaoDivida findSituacaoDivida(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SituacaoDivida.class, id);
        } finally {
            em.close();
        }
    }

    public int getSituacaoDividaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SituacaoDivida> rt = cq.from(SituacaoDivida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

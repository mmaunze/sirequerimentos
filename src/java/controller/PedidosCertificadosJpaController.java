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
import modelo.PedidosCertificados;

/**
 *
 * @author Meldo Maunze
 */
public class PedidosCertificadosJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public PedidosCertificadosJpaController(EntityManagerFactory emf) {
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
     * @param pedidosCertificados
     */
    public void create(PedidosCertificados pedidosCertificados) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = pedidosCertificados.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                pedidosCertificados.setPedido(pedido);
            }
            em.persist(pedidosCertificados);
            if (pedido != null) {
                pedido.getPedidosCertificadosList().add(pedidosCertificados);
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
     * @param pedidosCertificados
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(PedidosCertificados pedidosCertificados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidosCertificados persistentPedidosCertificados = em.find(PedidosCertificados.class, pedidosCertificados.getId());
            Pedido pedidoOld = persistentPedidosCertificados.getPedido();
            Pedido pedidoNew = pedidosCertificados.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                pedidosCertificados.setPedido(pedidoNew);
            }
            pedidosCertificados = em.merge(pedidosCertificados);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getPedidosCertificadosList().remove(pedidosCertificados);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getPedidosCertificadosList().add(pedidosCertificados);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = pedidosCertificados.getId();
                if (findPedidosCertificados(id) == null) {
                    throw new NonexistentEntityException("The pedidosCertificados with id " + id + " no longer exists.");
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
            PedidosCertificados pedidosCertificados;
            try {
                pedidosCertificados = em.getReference(PedidosCertificados.class, id);
                pedidosCertificados.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidosCertificados with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = pedidosCertificados.getPedido();
            if (pedido != null) {
                pedido.getPedidosCertificadosList().remove(pedidosCertificados);
                pedido = em.merge(pedido);
            }
            em.remove(pedidosCertificados);
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
    public List<PedidosCertificados> findPedidosCertificadosEntities() {
        return findPedidosCertificadosEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<PedidosCertificados> findPedidosCertificadosEntities(int maxResults, int firstResult) {
        return findPedidosCertificadosEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<PedidosCertificados> findPedidosCertificadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PedidosCertificados.class));
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
    public PedidosCertificados findPedidosCertificados(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidosCertificados.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getPedidosCertificadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PedidosCertificados> rt = cq.from(PedidosCertificados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

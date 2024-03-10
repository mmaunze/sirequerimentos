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
import modelo.TransferenciaCrs;

/**
 *
 * @author Meldo Maunze
 */
public class TransferenciaCrsJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public TransferenciaCrsJpaController(EntityManagerFactory emf) {
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
     * @param transferenciaCrs
     */
    public void create(TransferenciaCrs transferenciaCrs) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = transferenciaCrs.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                transferenciaCrs.setPedido(pedido);
            }
            em.persist(transferenciaCrs);
            if (pedido != null) {
                pedido.getTransferenciaCrsList().add(transferenciaCrs);
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
     * @param transferenciaCrs
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(TransferenciaCrs transferenciaCrs) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransferenciaCrs persistentTransferenciaCrs = em.find(TransferenciaCrs.class, transferenciaCrs.getId());
            Pedido pedidoOld = persistentTransferenciaCrs.getPedido();
            Pedido pedidoNew = transferenciaCrs.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                transferenciaCrs.setPedido(pedidoNew);
            }
            transferenciaCrs = em.merge(transferenciaCrs);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getTransferenciaCrsList().remove(transferenciaCrs);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getTransferenciaCrsList().add(transferenciaCrs);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = transferenciaCrs.getId();
                if (findTransferenciaCrs(id) == null) {
                    throw new NonexistentEntityException("The transferenciaCrs with id " + id + " no longer exists.");
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
            TransferenciaCrs transferenciaCrs;
            try {
                transferenciaCrs = em.getReference(TransferenciaCrs.class, id);
                transferenciaCrs.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transferenciaCrs with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = transferenciaCrs.getPedido();
            if (pedido != null) {
                pedido.getTransferenciaCrsList().remove(transferenciaCrs);
                pedido = em.merge(pedido);
            }
            em.remove(transferenciaCrs);
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
    public List<TransferenciaCrs> findTransferenciaCrsEntities() {
        return findTransferenciaCrsEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<TransferenciaCrs> findTransferenciaCrsEntities(int maxResults, int firstResult) {
        return findTransferenciaCrsEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<TransferenciaCrs> findTransferenciaCrsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransferenciaCrs.class));
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
    public TransferenciaCrs findTransferenciaCrs(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransferenciaCrs.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getTransferenciaCrsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransferenciaCrs> rt = cq.from(TransferenciaCrs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

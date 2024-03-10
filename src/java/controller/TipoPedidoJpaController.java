/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Pedido;
import modelo.TipoPedido;

/**
 *
 * @author Meldo Maunze
 */
public class TipoPedidoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public TipoPedidoJpaController(EntityManagerFactory emf) {
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
     * @param tipoPedido
     */
    public void create(TipoPedido tipoPedido) {
        if (tipoPedido.getPedidoList() == null) {
            tipoPedido.setPedidoList(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pedido> attachedPedidoList = new ArrayList<>();
            for (Pedido pedidoListPedidoToAttach : tipoPedido.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getId());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            tipoPedido.setPedidoList(attachedPedidoList);
            em.persist(tipoPedido);
            for (Pedido pedidoListPedido : tipoPedido.getPedidoList()) {
                TipoPedido oldTipoPedidoOfPedidoListPedido = pedidoListPedido.getTipoPedido();
                pedidoListPedido.setTipoPedido(tipoPedido);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldTipoPedidoOfPedidoListPedido != null) {
                    oldTipoPedidoOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldTipoPedidoOfPedidoListPedido = em.merge(oldTipoPedidoOfPedidoListPedido);
                }
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
     * @param tipoPedido
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(TipoPedido tipoPedido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPedido persistentTipoPedido = em.find(TipoPedido.class, tipoPedido.getId());
            List<Pedido> pedidoListOld = persistentTipoPedido.getPedidoList();
            List<Pedido> pedidoListNew = tipoPedido.getPedidoList();
            List<String> illegalOrphanMessages = null;
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its tipoPedido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getId());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            tipoPedido.setPedidoList(pedidoListNew);
            tipoPedido = em.merge(tipoPedido);
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    TipoPedido oldTipoPedidoOfPedidoListNewPedido = pedidoListNewPedido.getTipoPedido();
                    pedidoListNewPedido.setTipoPedido(tipoPedido);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldTipoPedidoOfPedidoListNewPedido != null && !oldTipoPedidoOfPedidoListNewPedido.equals(tipoPedido)) {
                        oldTipoPedidoOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldTipoPedidoOfPedidoListNewPedido = em.merge(oldTipoPedidoOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoPedido.getId();
                if (findTipoPedido(id) == null) {
                    throw new NonexistentEntityException("The tipoPedido with id " + id + " no longer exists.");
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
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     */
    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPedido tipoPedido;
            try {
                tipoPedido = em.getReference(TipoPedido.class, id);
                tipoPedido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPedido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pedido> pedidoListOrphanCheck = tipoPedido.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TipoPedido (" + tipoPedido + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable tipoPedido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPedido);
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
    public List<TipoPedido> findTipoPedidoEntities() {
        return findTipoPedidoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<TipoPedido> findTipoPedidoEntities(int maxResults, int firstResult) {
        return findTipoPedidoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<TipoPedido> findTipoPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPedido.class));
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
    public TipoPedido findTipoPedido(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPedido.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getTipoPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPedido> rt = cq.from(TipoPedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

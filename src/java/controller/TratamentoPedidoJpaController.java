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
import modelo.Secretario;
import modelo.TratamentoPedido;

/**
 *
 * @author Meldo Maunze
 */
public class TratamentoPedidoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public TratamentoPedidoJpaController(EntityManagerFactory emf) {
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
     * @param tratamentoPedido
     */
    public void create(TratamentoPedido tratamentoPedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = tratamentoPedido.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                tratamentoPedido.setPedido(pedido);
            }
            Secretario secretario = tratamentoPedido.getSecretario();
            if (secretario != null) {
                secretario = em.getReference(secretario.getClass(), secretario.getUtilizador());
                tratamentoPedido.setSecretario(secretario);
            }
            em.persist(tratamentoPedido);
            if (pedido != null) {
                pedido.getTratamentoPedidoList().add(tratamentoPedido);
                pedido = em.merge(pedido);
            }
            if (secretario != null) {
                secretario.getTratamentoPedidoList().add(tratamentoPedido);
                secretario = em.merge(secretario);
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
     * @param tratamentoPedido
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(TratamentoPedido tratamentoPedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TratamentoPedido persistentTratamentoPedido = em.find(TratamentoPedido.class, tratamentoPedido.getId());
            Pedido pedidoOld = persistentTratamentoPedido.getPedido();
            Pedido pedidoNew = tratamentoPedido.getPedido();
            Secretario secretarioOld = persistentTratamentoPedido.getSecretario();
            Secretario secretarioNew = tratamentoPedido.getSecretario();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                tratamentoPedido.setPedido(pedidoNew);
            }
            if (secretarioNew != null) {
                secretarioNew = em.getReference(secretarioNew.getClass(), secretarioNew.getUtilizador());
                tratamentoPedido.setSecretario(secretarioNew);
            }
            tratamentoPedido = em.merge(tratamentoPedido);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getTratamentoPedidoList().remove(tratamentoPedido);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getTratamentoPedidoList().add(tratamentoPedido);
                pedidoNew = em.merge(pedidoNew);
            }
            if (secretarioOld != null && !secretarioOld.equals(secretarioNew)) {
                secretarioOld.getTratamentoPedidoList().remove(tratamentoPedido);
                secretarioOld = em.merge(secretarioOld);
            }
            if (secretarioNew != null && !secretarioNew.equals(secretarioOld)) {
                secretarioNew.getTratamentoPedidoList().add(tratamentoPedido);
                secretarioNew = em.merge(secretarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tratamentoPedido.getId();
                if (findTratamentoPedido(id) == null) {
                    throw new NonexistentEntityException("The tratamentoPedido with id " + id + " no longer exists.");
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
            TratamentoPedido tratamentoPedido;
            try {
                tratamentoPedido = em.getReference(TratamentoPedido.class, id);
                tratamentoPedido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tratamentoPedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = tratamentoPedido.getPedido();
            if (pedido != null) {
                pedido.getTratamentoPedidoList().remove(tratamentoPedido);
                pedido = em.merge(pedido);
            }
            Secretario secretario = tratamentoPedido.getSecretario();
            if (secretario != null) {
                secretario.getTratamentoPedidoList().remove(tratamentoPedido);
                secretario = em.merge(secretario);
            }
            em.remove(tratamentoPedido);
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
    public List<TratamentoPedido> findTratamentoPedidoEntities() {
        return findTratamentoPedidoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<TratamentoPedido> findTratamentoPedidoEntities(int maxResults, int firstResult) {
        return findTratamentoPedidoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<TratamentoPedido> findTratamentoPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TratamentoPedido.class));
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
    public TratamentoPedido findTratamentoPedido(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TratamentoPedido.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getTratamentoPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TratamentoPedido> rt = cq.from(TratamentoPedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

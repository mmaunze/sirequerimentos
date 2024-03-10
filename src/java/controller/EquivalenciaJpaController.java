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
import modelo.Equivalencia;
import modelo.Pedido;

/**
 *
 * @author Meldo Maunze
 */
public class EquivalenciaJpaController implements Serializable {

    public EquivalenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equivalencia equivalencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = equivalencia.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                equivalencia.setPedido(pedido);
            }
            em.persist(equivalencia);
            if (pedido != null) {
                pedido.getEquivalenciaList().add(equivalencia);
                pedido = em.merge(pedido);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equivalencia equivalencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equivalencia persistentEquivalencia = em.find(Equivalencia.class, equivalencia.getId());
            Pedido pedidoOld = persistentEquivalencia.getPedido();
            Pedido pedidoNew = equivalencia.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                equivalencia.setPedido(pedidoNew);
            }
            equivalencia = em.merge(equivalencia);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getEquivalenciaList().remove(equivalencia);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getEquivalenciaList().add(equivalencia);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = equivalencia.getId();
                if (findEquivalencia(id) == null) {
                    throw new NonexistentEntityException("The equivalencia with id " + id + " no longer exists.");
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
            Equivalencia equivalencia;
            try {
                equivalencia = em.getReference(Equivalencia.class, id);
                equivalencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equivalencia with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = equivalencia.getPedido();
            if (pedido != null) {
                pedido.getEquivalenciaList().remove(equivalencia);
                pedido = em.merge(pedido);
            }
            em.remove(equivalencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equivalencia> findEquivalenciaEntities() {
        return findEquivalenciaEntities(true, -1, -1);
    }

    public List<Equivalencia> findEquivalenciaEntities(int maxResults, int firstResult) {
        return findEquivalenciaEntities(false, maxResults, firstResult);
    }

    private List<Equivalencia> findEquivalenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equivalencia.class));
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

    public Equivalencia findEquivalencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equivalencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquivalenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equivalencia> rt = cq.from(Equivalencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

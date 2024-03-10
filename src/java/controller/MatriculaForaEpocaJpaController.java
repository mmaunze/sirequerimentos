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
import modelo.MatriculaForaEpoca;
import modelo.Pedido;

/**
 *
 * @author Meldo Maunze
 */
public class MatriculaForaEpocaJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public MatriculaForaEpocaJpaController(EntityManagerFactory emf) {
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
     * @param matriculaForaEpoca
     */
    public void create(MatriculaForaEpoca matriculaForaEpoca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = matriculaForaEpoca.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                matriculaForaEpoca.setPedido(pedido);
            }
            em.persist(matriculaForaEpoca);
            if (pedido != null) {
                pedido.getMatriculaForaEpocaList().add(matriculaForaEpoca);
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
     * @param matriculaForaEpoca
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(MatriculaForaEpoca matriculaForaEpoca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MatriculaForaEpoca persistentMatriculaForaEpoca = em.find(MatriculaForaEpoca.class, matriculaForaEpoca.getId());
            Pedido pedidoOld = persistentMatriculaForaEpoca.getPedido();
            Pedido pedidoNew = matriculaForaEpoca.getPedido();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                matriculaForaEpoca.setPedido(pedidoNew);
            }
            matriculaForaEpoca = em.merge(matriculaForaEpoca);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getMatriculaForaEpocaList().remove(matriculaForaEpoca);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getMatriculaForaEpocaList().add(matriculaForaEpoca);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = matriculaForaEpoca.getId();
                if (findMatriculaForaEpoca(id) == null) {
                    throw new NonexistentEntityException("The matriculaForaEpoca with id " + id + " no longer exists.");
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
            MatriculaForaEpoca matriculaForaEpoca;
            try {
                matriculaForaEpoca = em.getReference(MatriculaForaEpoca.class, id);
                matriculaForaEpoca.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matriculaForaEpoca with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = matriculaForaEpoca.getPedido();
            if (pedido != null) {
                pedido.getMatriculaForaEpocaList().remove(matriculaForaEpoca);
                pedido = em.merge(pedido);
            }
            em.remove(matriculaForaEpoca);
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
    public List<MatriculaForaEpoca> findMatriculaForaEpocaEntities() {
        return findMatriculaForaEpocaEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<MatriculaForaEpoca> findMatriculaForaEpocaEntities(int maxResults, int firstResult) {
        return findMatriculaForaEpocaEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<MatriculaForaEpoca> findMatriculaForaEpocaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MatriculaForaEpoca.class));
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
    public MatriculaForaEpoca findMatriculaForaEpoca(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MatriculaForaEpoca.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getMatriculaForaEpocaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MatriculaForaEpoca> rt = cq.from(MatriculaForaEpoca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

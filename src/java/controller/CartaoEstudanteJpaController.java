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
import modelo.CartaoEstudante;

/**
 *
 * @author Meldo Maunze
 */
public class CartaoEstudanteJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public CartaoEstudanteJpaController(EntityManagerFactory emf) {
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
     * @param cartaoEstudante
     */
    public void create(CartaoEstudante cartaoEstudante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cartaoEstudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @param cartaoEstudante
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(CartaoEstudante cartaoEstudante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cartaoEstudante = em.merge(cartaoEstudante);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cartaoEstudante.getId();
                if (findCartaoEstudante(id) == null) {
                    throw new NonexistentEntityException("The cartaoEstudante with id " + id + " no longer exists.");
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
            CartaoEstudante cartaoEstudante;
            try {
                cartaoEstudante = em.getReference(CartaoEstudante.class, id);
                cartaoEstudante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cartaoEstudante with id " + id + " no longer exists.", enfe);
            }
            em.remove(cartaoEstudante);
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
    public List<CartaoEstudante> findCartaoEstudanteEntities() {
        return findCartaoEstudanteEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<CartaoEstudante> findCartaoEstudanteEntities(int maxResults, int firstResult) {
        return findCartaoEstudanteEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<CartaoEstudante> findCartaoEstudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CartaoEstudante.class));
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
    public CartaoEstudante findCartaoEstudante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CartaoEstudante.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getCartaoEstudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CartaoEstudante> rt = cq.from(CartaoEstudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

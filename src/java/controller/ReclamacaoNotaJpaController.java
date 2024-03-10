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
import modelo.ReclamacaoNota;

/**
 *
 * @author Meldo Maunze
 */
public class ReclamacaoNotaJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public ReclamacaoNotaJpaController(EntityManagerFactory emf) {
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
     * @param reclamacaoNota
     */
    public void create(ReclamacaoNota reclamacaoNota) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(reclamacaoNota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @param reclamacaoNota
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(ReclamacaoNota reclamacaoNota) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            reclamacaoNota = em.merge(reclamacaoNota);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = reclamacaoNota.getId();
                if (findReclamacaoNota(id) == null) {
                    throw new NonexistentEntityException("The reclamacaoNota with id " + id + " no longer exists.");
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
            ReclamacaoNota reclamacaoNota;
            try {
                reclamacaoNota = em.getReference(ReclamacaoNota.class, id);
                reclamacaoNota.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reclamacaoNota with id " + id + " no longer exists.", enfe);
            }
            em.remove(reclamacaoNota);
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
    public List<ReclamacaoNota> findReclamacaoNotaEntities() {
        return findReclamacaoNotaEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<ReclamacaoNota> findReclamacaoNotaEntities(int maxResults, int firstResult) {
        return findReclamacaoNotaEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<ReclamacaoNota> findReclamacaoNotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReclamacaoNota.class));
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
    public ReclamacaoNota findReclamacaoNota(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReclamacaoNota.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getReclamacaoNotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReclamacaoNota> rt = cq.from(ReclamacaoNota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

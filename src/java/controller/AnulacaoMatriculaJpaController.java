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
import modelo.AnulacaoMatricula;

/**
 *
 * @author Meldo Maunze
 */
public class AnulacaoMatriculaJpaController implements Serializable {

    public AnulacaoMatriculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AnulacaoMatricula anulacaoMatricula) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(anulacaoMatricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AnulacaoMatricula anulacaoMatricula) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            anulacaoMatricula = em.merge(anulacaoMatricula);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = anulacaoMatricula.getId();
                if (findAnulacaoMatricula(id) == null) {
                    throw new NonexistentEntityException("The anulacaoMatricula with id " + id + " no longer exists.");
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
            AnulacaoMatricula anulacaoMatricula;
            try {
                anulacaoMatricula = em.getReference(AnulacaoMatricula.class, id);
                anulacaoMatricula.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anulacaoMatricula with id " + id + " no longer exists.", enfe);
            }
            em.remove(anulacaoMatricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AnulacaoMatricula> findAnulacaoMatriculaEntities() {
        return findAnulacaoMatriculaEntities(true, -1, -1);
    }

    public List<AnulacaoMatricula> findAnulacaoMatriculaEntities(int maxResults, int firstResult) {
        return findAnulacaoMatriculaEntities(false, maxResults, firstResult);
    }

    private List<AnulacaoMatricula> findAnulacaoMatriculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AnulacaoMatricula.class));
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

    public AnulacaoMatricula findAnulacaoMatricula(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnulacaoMatricula.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnulacaoMatriculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AnulacaoMatricula> rt = cq.from(AnulacaoMatricula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
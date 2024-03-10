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
import modelo.MudancaPagamentoPropinas;

/**
 *
 * @author Meldo Maunze
 */
public class MudancaPagamentoPropinasJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public MudancaPagamentoPropinasJpaController(EntityManagerFactory emf) {
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
     * @param mudancaPagamentoPropinas
     */
    public void create(MudancaPagamentoPropinas mudancaPagamentoPropinas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mudancaPagamentoPropinas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @param mudancaPagamentoPropinas
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(MudancaPagamentoPropinas mudancaPagamentoPropinas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mudancaPagamentoPropinas = em.merge(mudancaPagamentoPropinas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = mudancaPagamentoPropinas.getId();
                if (findMudancaPagamentoPropinas(id) == null) {
                    throw new NonexistentEntityException("The mudancaPagamentoPropinas with id " + id + " no longer exists.");
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
            MudancaPagamentoPropinas mudancaPagamentoPropinas;
            try {
                mudancaPagamentoPropinas = em.getReference(MudancaPagamentoPropinas.class, id);
                mudancaPagamentoPropinas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mudancaPagamentoPropinas with id " + id + " no longer exists.", enfe);
            }
            em.remove(mudancaPagamentoPropinas);
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
    public List<MudancaPagamentoPropinas> findMudancaPagamentoPropinasEntities() {
        return findMudancaPagamentoPropinasEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<MudancaPagamentoPropinas> findMudancaPagamentoPropinasEntities(int maxResults, int firstResult) {
        return findMudancaPagamentoPropinasEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<MudancaPagamentoPropinas> findMudancaPagamentoPropinasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MudancaPagamentoPropinas.class));
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
    public MudancaPagamentoPropinas findMudancaPagamentoPropinas(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MudancaPagamentoPropinas.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getMudancaPagamentoPropinasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MudancaPagamentoPropinas> rt = cq.from(MudancaPagamentoPropinas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

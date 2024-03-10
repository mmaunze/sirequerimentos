/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cargo;
import modelo.Utilizador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cta;

/**
 *
 * @author Meldo Maunze
 */
public class CtaJpaController implements Serializable {

    public CtaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cta cta) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Utilizador utilizador1OrphanCheck = cta.getUtilizador1();
        if (utilizador1OrphanCheck != null) {
            Cta oldCtaOfUtilizador1 = utilizador1OrphanCheck.getCta();
            if (oldCtaOfUtilizador1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Utilizador " + utilizador1OrphanCheck + " already has an item of type Cta whose utilizador1 column cannot be null. Please make another selection for the utilizador1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo cargo = cta.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getId());
                cta.setCargo(cargo);
            }
            Utilizador utilizador1 = cta.getUtilizador1();
            if (utilizador1 != null) {
                utilizador1 = em.getReference(utilizador1.getClass(), utilizador1.getId());
                cta.setUtilizador1(utilizador1);
            }
            em.persist(cta);
            if (cargo != null) {
                cargo.getCtaList().add(cta);
                cargo = em.merge(cargo);
            }
            if (utilizador1 != null) {
                utilizador1.setCta(cta);
                utilizador1 = em.merge(utilizador1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCta(cta.getUtilizador()) != null) {
                throw new PreexistingEntityException("Cta " + cta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cta cta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cta persistentCta = em.find(Cta.class, cta.getUtilizador());
            Cargo cargoOld = persistentCta.getCargo();
            Cargo cargoNew = cta.getCargo();
            Utilizador utilizador1Old = persistentCta.getUtilizador1();
            Utilizador utilizador1New = cta.getUtilizador1();
            List<String> illegalOrphanMessages = null;
            if (utilizador1New != null && !utilizador1New.equals(utilizador1Old)) {
                Cta oldCtaOfUtilizador1 = utilizador1New.getCta();
                if (oldCtaOfUtilizador1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Utilizador " + utilizador1New + " already has an item of type Cta whose utilizador1 column cannot be null. Please make another selection for the utilizador1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getId());
                cta.setCargo(cargoNew);
            }
            if (utilizador1New != null) {
                utilizador1New = em.getReference(utilizador1New.getClass(), utilizador1New.getId());
                cta.setUtilizador1(utilizador1New);
            }
            cta = em.merge(cta);
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getCtaList().remove(cta);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getCtaList().add(cta);
                cargoNew = em.merge(cargoNew);
            }
            if (utilizador1Old != null && !utilizador1Old.equals(utilizador1New)) {
                utilizador1Old.setCta(null);
                utilizador1Old = em.merge(utilizador1Old);
            }
            if (utilizador1New != null && !utilizador1New.equals(utilizador1Old)) {
                utilizador1New.setCta(cta);
                utilizador1New = em.merge(utilizador1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cta.getUtilizador();
                if (findCta(id) == null) {
                    throw new NonexistentEntityException("The cta with id " + id + " no longer exists.");
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
            Cta cta;
            try {
                cta = em.getReference(Cta.class, id);
                cta.getUtilizador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cta with id " + id + " no longer exists.", enfe);
            }
            Cargo cargo = cta.getCargo();
            if (cargo != null) {
                cargo.getCtaList().remove(cta);
                cargo = em.merge(cargo);
            }
            Utilizador utilizador1 = cta.getUtilizador1();
            if (utilizador1 != null) {
                utilizador1.setCta(null);
                utilizador1 = em.merge(utilizador1);
            }
            em.remove(cta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cta> findCtaEntities() {
        return findCtaEntities(true, -1, -1);
    }

    public List<Cta> findCtaEntities(int maxResults, int firstResult) {
        return findCtaEntities(false, maxResults, firstResult);
    }

    private List<Cta> findCtaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cta.class));
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

    public Cta findCta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCtaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cta> rt = cq.from(Cta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

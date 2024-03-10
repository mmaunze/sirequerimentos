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
import modelo.Cargo;
import modelo.Cta;

/**
 *
 * @author Meldo Maunze
 */
public class CargoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public CargoJpaController(EntityManagerFactory emf) {
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
     * @param cargo
     */
    public void create(Cargo cargo) {
        if (cargo.getCtaList() == null) {
            cargo.setCtaList(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cta> attachedCtaList = new ArrayList<>();
            for (Cta ctaListCtaToAttach : cargo.getCtaList()) {
                ctaListCtaToAttach = em.getReference(ctaListCtaToAttach.getClass(), ctaListCtaToAttach.getUtilizador());
                attachedCtaList.add(ctaListCtaToAttach);
            }
            cargo.setCtaList(attachedCtaList);
            em.persist(cargo);
            for (Cta ctaListCta : cargo.getCtaList()) {
                Cargo oldCargoOfCtaListCta = ctaListCta.getCargo();
                ctaListCta.setCargo(cargo);
                ctaListCta = em.merge(ctaListCta);
                if (oldCargoOfCtaListCta != null) {
                    oldCargoOfCtaListCta.getCtaList().remove(ctaListCta);
                    oldCargoOfCtaListCta = em.merge(oldCargoOfCtaListCta);
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
     * @param cargo
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Cargo cargo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getId());
            List<Cta> ctaListOld = persistentCargo.getCtaList();
            List<Cta> ctaListNew = cargo.getCtaList();
            List<String> illegalOrphanMessages = null;
            for (Cta ctaListOldCta : ctaListOld) {
                if (!ctaListNew.contains(ctaListOldCta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Cta " + ctaListOldCta + " since its cargo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cta> attachedCtaListNew = new ArrayList<>();
            for (Cta ctaListNewCtaToAttach : ctaListNew) {
                ctaListNewCtaToAttach = em.getReference(ctaListNewCtaToAttach.getClass(), ctaListNewCtaToAttach.getUtilizador());
                attachedCtaListNew.add(ctaListNewCtaToAttach);
            }
            ctaListNew = attachedCtaListNew;
            cargo.setCtaList(ctaListNew);
            cargo = em.merge(cargo);
            for (Cta ctaListNewCta : ctaListNew) {
                if (!ctaListOld.contains(ctaListNewCta)) {
                    Cargo oldCargoOfCtaListNewCta = ctaListNewCta.getCargo();
                    ctaListNewCta.setCargo(cargo);
                    ctaListNewCta = em.merge(ctaListNewCta);
                    if (oldCargoOfCtaListNewCta != null && !oldCargoOfCtaListNewCta.equals(cargo)) {
                        oldCargoOfCtaListNewCta.getCtaList().remove(ctaListNewCta);
                        oldCargoOfCtaListNewCta = em.merge(oldCargoOfCtaListNewCta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cargo.getId();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
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
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cta> ctaListOrphanCheck = cargo.getCtaList();
            for (Cta ctaListOrphanCheckCta : ctaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the Cta " + ctaListOrphanCheckCta + " in its ctaList field has a non-nullable cargo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cargo);
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
    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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
    public Cargo findCargo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

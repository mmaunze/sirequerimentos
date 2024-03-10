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
import modelo.Movimento;
import modelo.TipoMovimento;

/**
 *
 * @author Meldo Maunze
 */
public class TipoMovimentoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public TipoMovimentoJpaController(EntityManagerFactory emf) {
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
     * @param tipoMovimento
     */
    public void create(TipoMovimento tipoMovimento) {
        if (tipoMovimento.getMovimentoList() == null) {
            tipoMovimento.setMovimentoList(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Movimento> attachedMovimentoList = new ArrayList<>();
            for (Movimento movimentoListMovimentoToAttach : tipoMovimento.getMovimentoList()) {
                movimentoListMovimentoToAttach = em.getReference(movimentoListMovimentoToAttach.getClass(), movimentoListMovimentoToAttach.getId());
                attachedMovimentoList.add(movimentoListMovimentoToAttach);
            }
            tipoMovimento.setMovimentoList(attachedMovimentoList);
            em.persist(tipoMovimento);
            for (Movimento movimentoListMovimento : tipoMovimento.getMovimentoList()) {
                TipoMovimento oldTipoMovimentoOfMovimentoListMovimento = movimentoListMovimento.getTipoMovimento();
                movimentoListMovimento.setTipoMovimento(tipoMovimento);
                movimentoListMovimento = em.merge(movimentoListMovimento);
                if (oldTipoMovimentoOfMovimentoListMovimento != null) {
                    oldTipoMovimentoOfMovimentoListMovimento.getMovimentoList().remove(movimentoListMovimento);
                    oldTipoMovimentoOfMovimentoListMovimento = em.merge(oldTipoMovimentoOfMovimentoListMovimento);
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
     * @param tipoMovimento
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(TipoMovimento tipoMovimento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMovimento persistentTipoMovimento = em.find(TipoMovimento.class, tipoMovimento.getId());
            List<Movimento> movimentoListOld = persistentTipoMovimento.getMovimentoList();
            List<Movimento> movimentoListNew = tipoMovimento.getMovimentoList();
            List<String> illegalOrphanMessages = null;
            for (Movimento movimentoListOldMovimento : movimentoListOld) {
                if (!movimentoListNew.contains(movimentoListOldMovimento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Movimento " + movimentoListOldMovimento + " since its tipoMovimento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Movimento> attachedMovimentoListNew = new ArrayList<>();
            for (Movimento movimentoListNewMovimentoToAttach : movimentoListNew) {
                movimentoListNewMovimentoToAttach = em.getReference(movimentoListNewMovimentoToAttach.getClass(), movimentoListNewMovimentoToAttach.getId());
                attachedMovimentoListNew.add(movimentoListNewMovimentoToAttach);
            }
            movimentoListNew = attachedMovimentoListNew;
            tipoMovimento.setMovimentoList(movimentoListNew);
            tipoMovimento = em.merge(tipoMovimento);
            for (Movimento movimentoListNewMovimento : movimentoListNew) {
                if (!movimentoListOld.contains(movimentoListNewMovimento)) {
                    TipoMovimento oldTipoMovimentoOfMovimentoListNewMovimento = movimentoListNewMovimento.getTipoMovimento();
                    movimentoListNewMovimento.setTipoMovimento(tipoMovimento);
                    movimentoListNewMovimento = em.merge(movimentoListNewMovimento);
                    if (oldTipoMovimentoOfMovimentoListNewMovimento != null && !oldTipoMovimentoOfMovimentoListNewMovimento.equals(tipoMovimento)) {
                        oldTipoMovimentoOfMovimentoListNewMovimento.getMovimentoList().remove(movimentoListNewMovimento);
                        oldTipoMovimentoOfMovimentoListNewMovimento = em.merge(oldTipoMovimentoOfMovimentoListNewMovimento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoMovimento.getId();
                if (findTipoMovimento(id) == null) {
                    throw new NonexistentEntityException("The tipoMovimento with id " + id + " no longer exists.");
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
            TipoMovimento tipoMovimento;
            try {
                tipoMovimento = em.getReference(TipoMovimento.class, id);
                tipoMovimento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoMovimento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Movimento> movimentoListOrphanCheck = tipoMovimento.getMovimentoList();
            for (Movimento movimentoListOrphanCheckMovimento : movimentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TipoMovimento (" + tipoMovimento + ") cannot be destroyed since the Movimento " + movimentoListOrphanCheckMovimento + " in its movimentoList field has a non-nullable tipoMovimento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoMovimento);
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
    public List<TipoMovimento> findTipoMovimentoEntities() {
        return findTipoMovimentoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<TipoMovimento> findTipoMovimentoEntities(int maxResults, int firstResult) {
        return findTipoMovimentoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<TipoMovimento> findTipoMovimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoMovimento.class));
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
    public TipoMovimento findTipoMovimento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoMovimento.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getTipoMovimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoMovimento> rt = cq.from(TipoMovimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

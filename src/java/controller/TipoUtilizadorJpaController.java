/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Utilizador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.TipoUtilizador;

/**
 *
 * @author Meldo Maunze
 */
public class TipoUtilizadorJpaController implements Serializable {

    public TipoUtilizadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUtilizador tipoUtilizador) {
        if (tipoUtilizador.getUtilizadorList() == null) {
            tipoUtilizador.setUtilizadorList(new ArrayList<Utilizador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Utilizador> attachedUtilizadorList = new ArrayList<Utilizador>();
            for (Utilizador utilizadorListUtilizadorToAttach : tipoUtilizador.getUtilizadorList()) {
                utilizadorListUtilizadorToAttach = em.getReference(utilizadorListUtilizadorToAttach.getClass(), utilizadorListUtilizadorToAttach.getId());
                attachedUtilizadorList.add(utilizadorListUtilizadorToAttach);
            }
            tipoUtilizador.setUtilizadorList(attachedUtilizadorList);
            em.persist(tipoUtilizador);
            for (Utilizador utilizadorListUtilizador : tipoUtilizador.getUtilizadorList()) {
                TipoUtilizador oldTipoUtilizadorOfUtilizadorListUtilizador = utilizadorListUtilizador.getTipoUtilizador();
                utilizadorListUtilizador.setTipoUtilizador(tipoUtilizador);
                utilizadorListUtilizador = em.merge(utilizadorListUtilizador);
                if (oldTipoUtilizadorOfUtilizadorListUtilizador != null) {
                    oldTipoUtilizadorOfUtilizadorListUtilizador.getUtilizadorList().remove(utilizadorListUtilizador);
                    oldTipoUtilizadorOfUtilizadorListUtilizador = em.merge(oldTipoUtilizadorOfUtilizadorListUtilizador);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUtilizador tipoUtilizador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUtilizador persistentTipoUtilizador = em.find(TipoUtilizador.class, tipoUtilizador.getId());
            List<Utilizador> utilizadorListOld = persistentTipoUtilizador.getUtilizadorList();
            List<Utilizador> utilizadorListNew = tipoUtilizador.getUtilizadorList();
            List<String> illegalOrphanMessages = null;
            for (Utilizador utilizadorListOldUtilizador : utilizadorListOld) {
                if (!utilizadorListNew.contains(utilizadorListOldUtilizador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Utilizador " + utilizadorListOldUtilizador + " since its tipoUtilizador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Utilizador> attachedUtilizadorListNew = new ArrayList<Utilizador>();
            for (Utilizador utilizadorListNewUtilizadorToAttach : utilizadorListNew) {
                utilizadorListNewUtilizadorToAttach = em.getReference(utilizadorListNewUtilizadorToAttach.getClass(), utilizadorListNewUtilizadorToAttach.getId());
                attachedUtilizadorListNew.add(utilizadorListNewUtilizadorToAttach);
            }
            utilizadorListNew = attachedUtilizadorListNew;
            tipoUtilizador.setUtilizadorList(utilizadorListNew);
            tipoUtilizador = em.merge(tipoUtilizador);
            for (Utilizador utilizadorListNewUtilizador : utilizadorListNew) {
                if (!utilizadorListOld.contains(utilizadorListNewUtilizador)) {
                    TipoUtilizador oldTipoUtilizadorOfUtilizadorListNewUtilizador = utilizadorListNewUtilizador.getTipoUtilizador();
                    utilizadorListNewUtilizador.setTipoUtilizador(tipoUtilizador);
                    utilizadorListNewUtilizador = em.merge(utilizadorListNewUtilizador);
                    if (oldTipoUtilizadorOfUtilizadorListNewUtilizador != null && !oldTipoUtilizadorOfUtilizadorListNewUtilizador.equals(tipoUtilizador)) {
                        oldTipoUtilizadorOfUtilizadorListNewUtilizador.getUtilizadorList().remove(utilizadorListNewUtilizador);
                        oldTipoUtilizadorOfUtilizadorListNewUtilizador = em.merge(oldTipoUtilizadorOfUtilizadorListNewUtilizador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoUtilizador.getId();
                if (findTipoUtilizador(id) == null) {
                    throw new NonexistentEntityException("The tipoUtilizador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUtilizador tipoUtilizador;
            try {
                tipoUtilizador = em.getReference(TipoUtilizador.class, id);
                tipoUtilizador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUtilizador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Utilizador> utilizadorListOrphanCheck = tipoUtilizador.getUtilizadorList();
            for (Utilizador utilizadorListOrphanCheckUtilizador : utilizadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoUtilizador (" + tipoUtilizador + ") cannot be destroyed since the Utilizador " + utilizadorListOrphanCheckUtilizador + " in its utilizadorList field has a non-nullable tipoUtilizador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoUtilizador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUtilizador> findTipoUtilizadorEntities() {
        return findTipoUtilizadorEntities(true, -1, -1);
    }

    public List<TipoUtilizador> findTipoUtilizadorEntities(int maxResults, int firstResult) {
        return findTipoUtilizadorEntities(false, maxResults, firstResult);
    }

    private List<TipoUtilizador> findTipoUtilizadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUtilizador.class));
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

    public TipoUtilizador findTipoUtilizador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUtilizador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUtilizadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUtilizador> rt = cq.from(TipoUtilizador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

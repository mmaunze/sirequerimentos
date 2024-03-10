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
import modelo.Departamento;

/**
 *
 * @author Meldo Maunze
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getUtilizadorList() == null) {
            departamento.setUtilizadorList(new ArrayList<Utilizador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Utilizador> attachedUtilizadorList = new ArrayList<Utilizador>();
            for (Utilizador utilizadorListUtilizadorToAttach : departamento.getUtilizadorList()) {
                utilizadorListUtilizadorToAttach = em.getReference(utilizadorListUtilizadorToAttach.getClass(), utilizadorListUtilizadorToAttach.getId());
                attachedUtilizadorList.add(utilizadorListUtilizadorToAttach);
            }
            departamento.setUtilizadorList(attachedUtilizadorList);
            em.persist(departamento);
            for (Utilizador utilizadorListUtilizador : departamento.getUtilizadorList()) {
                Departamento oldDepartamentoOfUtilizadorListUtilizador = utilizadorListUtilizador.getDepartamento();
                utilizadorListUtilizador.setDepartamento(departamento);
                utilizadorListUtilizador = em.merge(utilizadorListUtilizador);
                if (oldDepartamentoOfUtilizadorListUtilizador != null) {
                    oldDepartamentoOfUtilizadorListUtilizador.getUtilizadorList().remove(utilizadorListUtilizador);
                    oldDepartamentoOfUtilizadorListUtilizador = em.merge(oldDepartamentoOfUtilizadorListUtilizador);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getId());
            List<Utilizador> utilizadorListOld = persistentDepartamento.getUtilizadorList();
            List<Utilizador> utilizadorListNew = departamento.getUtilizadorList();
            List<String> illegalOrphanMessages = null;
            for (Utilizador utilizadorListOldUtilizador : utilizadorListOld) {
                if (!utilizadorListNew.contains(utilizadorListOldUtilizador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Utilizador " + utilizadorListOldUtilizador + " since its departamento field is not nullable.");
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
            departamento.setUtilizadorList(utilizadorListNew);
            departamento = em.merge(departamento);
            for (Utilizador utilizadorListNewUtilizador : utilizadorListNew) {
                if (!utilizadorListOld.contains(utilizadorListNewUtilizador)) {
                    Departamento oldDepartamentoOfUtilizadorListNewUtilizador = utilizadorListNewUtilizador.getDepartamento();
                    utilizadorListNewUtilizador.setDepartamento(departamento);
                    utilizadorListNewUtilizador = em.merge(utilizadorListNewUtilizador);
                    if (oldDepartamentoOfUtilizadorListNewUtilizador != null && !oldDepartamentoOfUtilizadorListNewUtilizador.equals(departamento)) {
                        oldDepartamentoOfUtilizadorListNewUtilizador.getUtilizadorList().remove(utilizadorListNewUtilizador);
                        oldDepartamentoOfUtilizadorListNewUtilizador = em.merge(oldDepartamentoOfUtilizadorListNewUtilizador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = departamento.getId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Utilizador> utilizadorListOrphanCheck = departamento.getUtilizadorList();
            for (Utilizador utilizadorListOrphanCheckUtilizador : utilizadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Utilizador " + utilizadorListOrphanCheckUtilizador + " in its utilizadorList field has a non-nullable departamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

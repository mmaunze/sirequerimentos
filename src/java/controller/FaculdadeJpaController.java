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
import modelo.Secretario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Curso;
import modelo.Faculdade;

/**
 *
 * @author Meldo Maunze
 */
public class FaculdadeJpaController implements Serializable {

    public FaculdadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Faculdade faculdade) {
        if (faculdade.getSecretarioList() == null) {
            faculdade.setSecretarioList(new ArrayList<Secretario>());
        }
        if (faculdade.getCursoList() == null) {
            faculdade.setCursoList(new ArrayList<Curso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Secretario> attachedSecretarioList = new ArrayList<Secretario>();
            for (Secretario secretarioListSecretarioToAttach : faculdade.getSecretarioList()) {
                secretarioListSecretarioToAttach = em.getReference(secretarioListSecretarioToAttach.getClass(), secretarioListSecretarioToAttach.getUtilizador());
                attachedSecretarioList.add(secretarioListSecretarioToAttach);
            }
            faculdade.setSecretarioList(attachedSecretarioList);
            List<Curso> attachedCursoList = new ArrayList<Curso>();
            for (Curso cursoListCursoToAttach : faculdade.getCursoList()) {
                cursoListCursoToAttach = em.getReference(cursoListCursoToAttach.getClass(), cursoListCursoToAttach.getId());
                attachedCursoList.add(cursoListCursoToAttach);
            }
            faculdade.setCursoList(attachedCursoList);
            em.persist(faculdade);
            for (Secretario secretarioListSecretario : faculdade.getSecretarioList()) {
                Faculdade oldFaculdadeOfSecretarioListSecretario = secretarioListSecretario.getFaculdade();
                secretarioListSecretario.setFaculdade(faculdade);
                secretarioListSecretario = em.merge(secretarioListSecretario);
                if (oldFaculdadeOfSecretarioListSecretario != null) {
                    oldFaculdadeOfSecretarioListSecretario.getSecretarioList().remove(secretarioListSecretario);
                    oldFaculdadeOfSecretarioListSecretario = em.merge(oldFaculdadeOfSecretarioListSecretario);
                }
            }
            for (Curso cursoListCurso : faculdade.getCursoList()) {
                Faculdade oldFaculdadeOfCursoListCurso = cursoListCurso.getFaculdade();
                cursoListCurso.setFaculdade(faculdade);
                cursoListCurso = em.merge(cursoListCurso);
                if (oldFaculdadeOfCursoListCurso != null) {
                    oldFaculdadeOfCursoListCurso.getCursoList().remove(cursoListCurso);
                    oldFaculdadeOfCursoListCurso = em.merge(oldFaculdadeOfCursoListCurso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Faculdade faculdade) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faculdade persistentFaculdade = em.find(Faculdade.class, faculdade.getId());
            List<Secretario> secretarioListOld = persistentFaculdade.getSecretarioList();
            List<Secretario> secretarioListNew = faculdade.getSecretarioList();
            List<Curso> cursoListOld = persistentFaculdade.getCursoList();
            List<Curso> cursoListNew = faculdade.getCursoList();
            List<String> illegalOrphanMessages = null;
            for (Secretario secretarioListOldSecretario : secretarioListOld) {
                if (!secretarioListNew.contains(secretarioListOldSecretario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Secretario " + secretarioListOldSecretario + " since its faculdade field is not nullable.");
                }
            }
            for (Curso cursoListOldCurso : cursoListOld) {
                if (!cursoListNew.contains(cursoListOldCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Curso " + cursoListOldCurso + " since its faculdade field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Secretario> attachedSecretarioListNew = new ArrayList<Secretario>();
            for (Secretario secretarioListNewSecretarioToAttach : secretarioListNew) {
                secretarioListNewSecretarioToAttach = em.getReference(secretarioListNewSecretarioToAttach.getClass(), secretarioListNewSecretarioToAttach.getUtilizador());
                attachedSecretarioListNew.add(secretarioListNewSecretarioToAttach);
            }
            secretarioListNew = attachedSecretarioListNew;
            faculdade.setSecretarioList(secretarioListNew);
            List<Curso> attachedCursoListNew = new ArrayList<Curso>();
            for (Curso cursoListNewCursoToAttach : cursoListNew) {
                cursoListNewCursoToAttach = em.getReference(cursoListNewCursoToAttach.getClass(), cursoListNewCursoToAttach.getId());
                attachedCursoListNew.add(cursoListNewCursoToAttach);
            }
            cursoListNew = attachedCursoListNew;
            faculdade.setCursoList(cursoListNew);
            faculdade = em.merge(faculdade);
            for (Secretario secretarioListNewSecretario : secretarioListNew) {
                if (!secretarioListOld.contains(secretarioListNewSecretario)) {
                    Faculdade oldFaculdadeOfSecretarioListNewSecretario = secretarioListNewSecretario.getFaculdade();
                    secretarioListNewSecretario.setFaculdade(faculdade);
                    secretarioListNewSecretario = em.merge(secretarioListNewSecretario);
                    if (oldFaculdadeOfSecretarioListNewSecretario != null && !oldFaculdadeOfSecretarioListNewSecretario.equals(faculdade)) {
                        oldFaculdadeOfSecretarioListNewSecretario.getSecretarioList().remove(secretarioListNewSecretario);
                        oldFaculdadeOfSecretarioListNewSecretario = em.merge(oldFaculdadeOfSecretarioListNewSecretario);
                    }
                }
            }
            for (Curso cursoListNewCurso : cursoListNew) {
                if (!cursoListOld.contains(cursoListNewCurso)) {
                    Faculdade oldFaculdadeOfCursoListNewCurso = cursoListNewCurso.getFaculdade();
                    cursoListNewCurso.setFaculdade(faculdade);
                    cursoListNewCurso = em.merge(cursoListNewCurso);
                    if (oldFaculdadeOfCursoListNewCurso != null && !oldFaculdadeOfCursoListNewCurso.equals(faculdade)) {
                        oldFaculdadeOfCursoListNewCurso.getCursoList().remove(cursoListNewCurso);
                        oldFaculdadeOfCursoListNewCurso = em.merge(oldFaculdadeOfCursoListNewCurso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = faculdade.getId();
                if (findFaculdade(id) == null) {
                    throw new NonexistentEntityException("The faculdade with id " + id + " no longer exists.");
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
            Faculdade faculdade;
            try {
                faculdade = em.getReference(Faculdade.class, id);
                faculdade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The faculdade with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Secretario> secretarioListOrphanCheck = faculdade.getSecretarioList();
            for (Secretario secretarioListOrphanCheckSecretario : secretarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Faculdade (" + faculdade + ") cannot be destroyed since the Secretario " + secretarioListOrphanCheckSecretario + " in its secretarioList field has a non-nullable faculdade field.");
            }
            List<Curso> cursoListOrphanCheck = faculdade.getCursoList();
            for (Curso cursoListOrphanCheckCurso : cursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Faculdade (" + faculdade + ") cannot be destroyed since the Curso " + cursoListOrphanCheckCurso + " in its cursoList field has a non-nullable faculdade field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(faculdade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Faculdade> findFaculdadeEntities() {
        return findFaculdadeEntities(true, -1, -1);
    }

    public List<Faculdade> findFaculdadeEntities(int maxResults, int firstResult) {
        return findFaculdadeEntities(false, maxResults, firstResult);
    }

    private List<Faculdade> findFaculdadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faculdade.class));
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

    public Faculdade findFaculdade(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faculdade.class, id);
        } finally {
            em.close();
        }
    }

    public int getFaculdadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faculdade> rt = cq.from(Faculdade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import modelo.Curso;
import modelo.Utilizador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Estudante;

/**
 *
 * @author Meldo Maunze
 */
public class EstudanteJpaController implements Serializable {

    public EstudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudante estudante) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Utilizador utilizador1OrphanCheck = estudante.getUtilizador1();
        if (utilizador1OrphanCheck != null) {
            Estudante oldEstudanteOfUtilizador1 = utilizador1OrphanCheck.getEstudante();
            if (oldEstudanteOfUtilizador1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Utilizador " + utilizador1OrphanCheck + " already has an item of type Estudante whose utilizador1 column cannot be null. Please make another selection for the utilizador1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso curso = estudante.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getId());
                estudante.setCurso(curso);
            }
            Utilizador utilizador1 = estudante.getUtilizador1();
            if (utilizador1 != null) {
                utilizador1 = em.getReference(utilizador1.getClass(), utilizador1.getId());
                estudante.setUtilizador1(utilizador1);
            }
            em.persist(estudante);
            if (curso != null) {
                curso.getEstudanteList().add(estudante);
                curso = em.merge(curso);
            }
            if (utilizador1 != null) {
                utilizador1.setEstudante(estudante);
                utilizador1 = em.merge(utilizador1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudante(estudante.getUtilizador()) != null) {
                throw new PreexistingEntityException("Estudante " + estudante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudante estudante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante persistentEstudante = em.find(Estudante.class, estudante.getUtilizador());
            Curso cursoOld = persistentEstudante.getCurso();
            Curso cursoNew = estudante.getCurso();
            Utilizador utilizador1Old = persistentEstudante.getUtilizador1();
            Utilizador utilizador1New = estudante.getUtilizador1();
            List<String> illegalOrphanMessages = null;
            if (utilizador1New != null && !utilizador1New.equals(utilizador1Old)) {
                Estudante oldEstudanteOfUtilizador1 = utilizador1New.getEstudante();
                if (oldEstudanteOfUtilizador1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Utilizador " + utilizador1New + " already has an item of type Estudante whose utilizador1 column cannot be null. Please make another selection for the utilizador1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getId());
                estudante.setCurso(cursoNew);
            }
            if (utilizador1New != null) {
                utilizador1New = em.getReference(utilizador1New.getClass(), utilizador1New.getId());
                estudante.setUtilizador1(utilizador1New);
            }
            estudante = em.merge(estudante);
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getEstudanteList().remove(estudante);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getEstudanteList().add(estudante);
                cursoNew = em.merge(cursoNew);
            }
            if (utilizador1Old != null && !utilizador1Old.equals(utilizador1New)) {
                utilizador1Old.setEstudante(null);
                utilizador1Old = em.merge(utilizador1Old);
            }
            if (utilizador1New != null && !utilizador1New.equals(utilizador1Old)) {
                utilizador1New.setEstudante(estudante);
                utilizador1New = em.merge(utilizador1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudante.getUtilizador();
                if (findEstudante(id) == null) {
                    throw new NonexistentEntityException("The estudante with id " + id + " no longer exists.");
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
            Estudante estudante;
            try {
                estudante = em.getReference(Estudante.class, id);
                estudante.getUtilizador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudante with id " + id + " no longer exists.", enfe);
            }
            Curso curso = estudante.getCurso();
            if (curso != null) {
                curso.getEstudanteList().remove(estudante);
                curso = em.merge(curso);
            }
            Utilizador utilizador1 = estudante.getUtilizador1();
            if (utilizador1 != null) {
                utilizador1.setEstudante(null);
                utilizador1 = em.merge(utilizador1);
            }
            em.remove(estudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudante> findEstudanteEntities() {
        return findEstudanteEntities(true, -1, -1);
    }

    public List<Estudante> findEstudanteEntities(int maxResults, int firstResult) {
        return findEstudanteEntities(false, maxResults, firstResult);
    }

    private List<Estudante> findEstudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudante.class));
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

    public Estudante findEstudante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudante> rt = cq.from(Estudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

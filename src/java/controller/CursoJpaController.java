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
import modelo.Curso;
import modelo.Estudante;
import modelo.Faculdade;
import modelo.MudancaCurso;

/**
 *
 * @author Meldo Maunze
 */
public class CursoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public CursoJpaController(EntityManagerFactory emf) {
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
     * @param curso
     */
    public void create(Curso curso) {
        if (curso.getMudancaCursoList() == null) {
            curso.setMudancaCursoList(new ArrayList<>());
        }
        if (curso.getMudancaCursoList1() == null) {
            curso.setMudancaCursoList1(new ArrayList<>());
        }
        if (curso.getEstudanteList() == null) {
            curso.setEstudanteList(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faculdade faculdade = curso.getFaculdade();
            if (faculdade != null) {
                faculdade = em.getReference(faculdade.getClass(), faculdade.getId());
                curso.setFaculdade(faculdade);
            }
            List<MudancaCurso> attachedMudancaCursoList = new ArrayList<>();
            for (MudancaCurso mudancaCursoListMudancaCursoToAttach : curso.getMudancaCursoList()) {
                mudancaCursoListMudancaCursoToAttach = em.getReference(mudancaCursoListMudancaCursoToAttach.getClass(), mudancaCursoListMudancaCursoToAttach.getId());
                attachedMudancaCursoList.add(mudancaCursoListMudancaCursoToAttach);
            }
            curso.setMudancaCursoList(attachedMudancaCursoList);
            List<MudancaCurso> attachedMudancaCursoList1 = new ArrayList<>();
            for (MudancaCurso mudancaCursoList1MudancaCursoToAttach : curso.getMudancaCursoList1()) {
                mudancaCursoList1MudancaCursoToAttach = em.getReference(mudancaCursoList1MudancaCursoToAttach.getClass(), mudancaCursoList1MudancaCursoToAttach.getId());
                attachedMudancaCursoList1.add(mudancaCursoList1MudancaCursoToAttach);
            }
            curso.setMudancaCursoList1(attachedMudancaCursoList1);
            List<Estudante> attachedEstudanteList = new ArrayList<>();
            for (Estudante estudanteListEstudanteToAttach : curso.getEstudanteList()) {
                estudanteListEstudanteToAttach = em.getReference(estudanteListEstudanteToAttach.getClass(), estudanteListEstudanteToAttach.getUtilizador());
                attachedEstudanteList.add(estudanteListEstudanteToAttach);
            }
            curso.setEstudanteList(attachedEstudanteList);
            em.persist(curso);
            if (faculdade != null) {
                faculdade.getCursoList().add(curso);
                faculdade = em.merge(faculdade);
            }
            for (MudancaCurso mudancaCursoListMudancaCurso : curso.getMudancaCursoList()) {
                Curso oldCursoDestinoOfMudancaCursoListMudancaCurso = mudancaCursoListMudancaCurso.getCursoDestino();
                mudancaCursoListMudancaCurso.setCursoDestino(curso);
                mudancaCursoListMudancaCurso = em.merge(mudancaCursoListMudancaCurso);
                if (oldCursoDestinoOfMudancaCursoListMudancaCurso != null) {
                    oldCursoDestinoOfMudancaCursoListMudancaCurso.getMudancaCursoList().remove(mudancaCursoListMudancaCurso);
                    oldCursoDestinoOfMudancaCursoListMudancaCurso = em.merge(oldCursoDestinoOfMudancaCursoListMudancaCurso);
                }
            }
            for (MudancaCurso mudancaCursoList1MudancaCurso : curso.getMudancaCursoList1()) {
                Curso oldCusoOrigemOfMudancaCursoList1MudancaCurso = mudancaCursoList1MudancaCurso.getCusoOrigem();
                mudancaCursoList1MudancaCurso.setCusoOrigem(curso);
                mudancaCursoList1MudancaCurso = em.merge(mudancaCursoList1MudancaCurso);
                if (oldCusoOrigemOfMudancaCursoList1MudancaCurso != null) {
                    oldCusoOrigemOfMudancaCursoList1MudancaCurso.getMudancaCursoList1().remove(mudancaCursoList1MudancaCurso);
                    oldCusoOrigemOfMudancaCursoList1MudancaCurso = em.merge(oldCusoOrigemOfMudancaCursoList1MudancaCurso);
                }
            }
            for (Estudante estudanteListEstudante : curso.getEstudanteList()) {
                Curso oldCursoOfEstudanteListEstudante = estudanteListEstudante.getCurso();
                estudanteListEstudante.setCurso(curso);
                estudanteListEstudante = em.merge(estudanteListEstudante);
                if (oldCursoOfEstudanteListEstudante != null) {
                    oldCursoOfEstudanteListEstudante.getEstudanteList().remove(estudanteListEstudante);
                    oldCursoOfEstudanteListEstudante = em.merge(oldCursoOfEstudanteListEstudante);
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
     * @param curso
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Curso curso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getId());
            Faculdade faculdadeOld = persistentCurso.getFaculdade();
            Faculdade faculdadeNew = curso.getFaculdade();
            List<MudancaCurso> mudancaCursoListOld = persistentCurso.getMudancaCursoList();
            List<MudancaCurso> mudancaCursoListNew = curso.getMudancaCursoList();
            List<MudancaCurso> mudancaCursoList1Old = persistentCurso.getMudancaCursoList1();
            List<MudancaCurso> mudancaCursoList1New = curso.getMudancaCursoList1();
            List<Estudante> estudanteListOld = persistentCurso.getEstudanteList();
            List<Estudante> estudanteListNew = curso.getEstudanteList();
            List<String> illegalOrphanMessages = null;
            for (MudancaCurso mudancaCursoListOldMudancaCurso : mudancaCursoListOld) {
                if (!mudancaCursoListNew.contains(mudancaCursoListOldMudancaCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain MudancaCurso " + mudancaCursoListOldMudancaCurso + " since its cursoDestino field is not nullable.");
                }
            }
            for (MudancaCurso mudancaCursoList1OldMudancaCurso : mudancaCursoList1Old) {
                if (!mudancaCursoList1New.contains(mudancaCursoList1OldMudancaCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain MudancaCurso " + mudancaCursoList1OldMudancaCurso + " since its cusoOrigem field is not nullable.");
                }
            }
            for (Estudante estudanteListOldEstudante : estudanteListOld) {
                if (!estudanteListNew.contains(estudanteListOldEstudante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Estudante " + estudanteListOldEstudante + " since its curso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (faculdadeNew != null) {
                faculdadeNew = em.getReference(faculdadeNew.getClass(), faculdadeNew.getId());
                curso.setFaculdade(faculdadeNew);
            }
            List<MudancaCurso> attachedMudancaCursoListNew = new ArrayList<>();
            for (MudancaCurso mudancaCursoListNewMudancaCursoToAttach : mudancaCursoListNew) {
                mudancaCursoListNewMudancaCursoToAttach = em.getReference(mudancaCursoListNewMudancaCursoToAttach.getClass(), mudancaCursoListNewMudancaCursoToAttach.getId());
                attachedMudancaCursoListNew.add(mudancaCursoListNewMudancaCursoToAttach);
            }
            mudancaCursoListNew = attachedMudancaCursoListNew;
            curso.setMudancaCursoList(mudancaCursoListNew);
            List<MudancaCurso> attachedMudancaCursoList1New = new ArrayList<>();
            for (MudancaCurso mudancaCursoList1NewMudancaCursoToAttach : mudancaCursoList1New) {
                mudancaCursoList1NewMudancaCursoToAttach = em.getReference(mudancaCursoList1NewMudancaCursoToAttach.getClass(), mudancaCursoList1NewMudancaCursoToAttach.getId());
                attachedMudancaCursoList1New.add(mudancaCursoList1NewMudancaCursoToAttach);
            }
            mudancaCursoList1New = attachedMudancaCursoList1New;
            curso.setMudancaCursoList1(mudancaCursoList1New);
            List<Estudante> attachedEstudanteListNew = new ArrayList<>();
            for (Estudante estudanteListNewEstudanteToAttach : estudanteListNew) {
                estudanteListNewEstudanteToAttach = em.getReference(estudanteListNewEstudanteToAttach.getClass(), estudanteListNewEstudanteToAttach.getUtilizador());
                attachedEstudanteListNew.add(estudanteListNewEstudanteToAttach);
            }
            estudanteListNew = attachedEstudanteListNew;
            curso.setEstudanteList(estudanteListNew);
            curso = em.merge(curso);
            if (faculdadeOld != null && !faculdadeOld.equals(faculdadeNew)) {
                faculdadeOld.getCursoList().remove(curso);
                faculdadeOld = em.merge(faculdadeOld);
            }
            if (faculdadeNew != null && !faculdadeNew.equals(faculdadeOld)) {
                faculdadeNew.getCursoList().add(curso);
                faculdadeNew = em.merge(faculdadeNew);
            }
            for (MudancaCurso mudancaCursoListNewMudancaCurso : mudancaCursoListNew) {
                if (!mudancaCursoListOld.contains(mudancaCursoListNewMudancaCurso)) {
                    Curso oldCursoDestinoOfMudancaCursoListNewMudancaCurso = mudancaCursoListNewMudancaCurso.getCursoDestino();
                    mudancaCursoListNewMudancaCurso.setCursoDestino(curso);
                    mudancaCursoListNewMudancaCurso = em.merge(mudancaCursoListNewMudancaCurso);
                    if (oldCursoDestinoOfMudancaCursoListNewMudancaCurso != null && !oldCursoDestinoOfMudancaCursoListNewMudancaCurso.equals(curso)) {
                        oldCursoDestinoOfMudancaCursoListNewMudancaCurso.getMudancaCursoList().remove(mudancaCursoListNewMudancaCurso);
                        oldCursoDestinoOfMudancaCursoListNewMudancaCurso = em.merge(oldCursoDestinoOfMudancaCursoListNewMudancaCurso);
                    }
                }
            }
            for (MudancaCurso mudancaCursoList1NewMudancaCurso : mudancaCursoList1New) {
                if (!mudancaCursoList1Old.contains(mudancaCursoList1NewMudancaCurso)) {
                    Curso oldCusoOrigemOfMudancaCursoList1NewMudancaCurso = mudancaCursoList1NewMudancaCurso.getCusoOrigem();
                    mudancaCursoList1NewMudancaCurso.setCusoOrigem(curso);
                    mudancaCursoList1NewMudancaCurso = em.merge(mudancaCursoList1NewMudancaCurso);
                    if (oldCusoOrigemOfMudancaCursoList1NewMudancaCurso != null && !oldCusoOrigemOfMudancaCursoList1NewMudancaCurso.equals(curso)) {
                        oldCusoOrigemOfMudancaCursoList1NewMudancaCurso.getMudancaCursoList1().remove(mudancaCursoList1NewMudancaCurso);
                        oldCusoOrigemOfMudancaCursoList1NewMudancaCurso = em.merge(oldCusoOrigemOfMudancaCursoList1NewMudancaCurso);
                    }
                }
            }
            for (Estudante estudanteListNewEstudante : estudanteListNew) {
                if (!estudanteListOld.contains(estudanteListNewEstudante)) {
                    Curso oldCursoOfEstudanteListNewEstudante = estudanteListNewEstudante.getCurso();
                    estudanteListNewEstudante.setCurso(curso);
                    estudanteListNewEstudante = em.merge(estudanteListNewEstudante);
                    if (oldCursoOfEstudanteListNewEstudante != null && !oldCursoOfEstudanteListNewEstudante.equals(curso)) {
                        oldCursoOfEstudanteListNewEstudante.getEstudanteList().remove(estudanteListNewEstudante);
                        oldCursoOfEstudanteListNewEstudante = em.merge(oldCursoOfEstudanteListNewEstudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = curso.getId();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<MudancaCurso> mudancaCursoListOrphanCheck = curso.getMudancaCursoList();
            for (MudancaCurso mudancaCursoListOrphanCheckMudancaCurso : mudancaCursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the MudancaCurso " + mudancaCursoListOrphanCheckMudancaCurso + " in its mudancaCursoList field has a non-nullable cursoDestino field.");
            }
            List<MudancaCurso> mudancaCursoList1OrphanCheck = curso.getMudancaCursoList1();
            for (MudancaCurso mudancaCursoList1OrphanCheckMudancaCurso : mudancaCursoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the MudancaCurso " + mudancaCursoList1OrphanCheckMudancaCurso + " in its mudancaCursoList1 field has a non-nullable cusoOrigem field.");
            }
            List<Estudante> estudanteListOrphanCheck = curso.getEstudanteList();
            for (Estudante estudanteListOrphanCheckEstudante : estudanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Estudante " + estudanteListOrphanCheckEstudante + " in its estudanteList field has a non-nullable curso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Faculdade faculdade = curso.getFaculdade();
            if (faculdade != null) {
                faculdade.getCursoList().remove(curso);
                faculdade = em.merge(faculdade);
            }
            em.remove(curso);
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
    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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
    public Curso findCurso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

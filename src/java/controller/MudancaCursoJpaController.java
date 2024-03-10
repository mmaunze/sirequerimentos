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
import modelo.Curso;
import modelo.MudancaCurso;
import modelo.Pedido;

/**
 *
 * @author Meldo Maunze
 */
public class MudancaCursoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public MudancaCursoJpaController(EntityManagerFactory emf) {
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
     * @param mudancaCurso
     */
    public void create(MudancaCurso mudancaCurso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso cursoDestino = mudancaCurso.getCursoDestino();
            if (cursoDestino != null) {
                cursoDestino = em.getReference(cursoDestino.getClass(), cursoDestino.getId());
                mudancaCurso.setCursoDestino(cursoDestino);
            }
            Curso cusoOrigem = mudancaCurso.getCusoOrigem();
            if (cusoOrigem != null) {
                cusoOrigem = em.getReference(cusoOrigem.getClass(), cusoOrigem.getId());
                mudancaCurso.setCusoOrigem(cusoOrigem);
            }
            Pedido pedido = mudancaCurso.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                mudancaCurso.setPedido(pedido);
            }
            em.persist(mudancaCurso);
            if (cursoDestino != null) {
                cursoDestino.getMudancaCursoList().add(mudancaCurso);
                cursoDestino = em.merge(cursoDestino);
            }
            if (cusoOrigem != null) {
                cusoOrigem.getMudancaCursoList().add(mudancaCurso);
                cusoOrigem = em.merge(cusoOrigem);
            }
            if (pedido != null) {
                pedido.getMudancaCursoList().add(mudancaCurso);
                pedido = em.merge(pedido);
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
     * @param mudancaCurso
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(MudancaCurso mudancaCurso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MudancaCurso persistentMudancaCurso = em.find(MudancaCurso.class, mudancaCurso.getId());
            Curso cursoDestinoOld = persistentMudancaCurso.getCursoDestino();
            Curso cursoDestinoNew = mudancaCurso.getCursoDestino();
            Curso cusoOrigemOld = persistentMudancaCurso.getCusoOrigem();
            Curso cusoOrigemNew = mudancaCurso.getCusoOrigem();
            Pedido pedidoOld = persistentMudancaCurso.getPedido();
            Pedido pedidoNew = mudancaCurso.getPedido();
            if (cursoDestinoNew != null) {
                cursoDestinoNew = em.getReference(cursoDestinoNew.getClass(), cursoDestinoNew.getId());
                mudancaCurso.setCursoDestino(cursoDestinoNew);
            }
            if (cusoOrigemNew != null) {
                cusoOrigemNew = em.getReference(cusoOrigemNew.getClass(), cusoOrigemNew.getId());
                mudancaCurso.setCusoOrigem(cusoOrigemNew);
            }
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                mudancaCurso.setPedido(pedidoNew);
            }
            mudancaCurso = em.merge(mudancaCurso);
            if (cursoDestinoOld != null && !cursoDestinoOld.equals(cursoDestinoNew)) {
                cursoDestinoOld.getMudancaCursoList().remove(mudancaCurso);
                cursoDestinoOld = em.merge(cursoDestinoOld);
            }
            if (cursoDestinoNew != null && !cursoDestinoNew.equals(cursoDestinoOld)) {
                cursoDestinoNew.getMudancaCursoList().add(mudancaCurso);
                cursoDestinoNew = em.merge(cursoDestinoNew);
            }
            if (cusoOrigemOld != null && !cusoOrigemOld.equals(cusoOrigemNew)) {
                cusoOrigemOld.getMudancaCursoList().remove(mudancaCurso);
                cusoOrigemOld = em.merge(cusoOrigemOld);
            }
            if (cusoOrigemNew != null && !cusoOrigemNew.equals(cusoOrigemOld)) {
                cusoOrigemNew.getMudancaCursoList().add(mudancaCurso);
                cusoOrigemNew = em.merge(cusoOrigemNew);
            }
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getMudancaCursoList().remove(mudancaCurso);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getMudancaCursoList().add(mudancaCurso);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = mudancaCurso.getId();
                if (findMudancaCurso(id) == null) {
                    throw new NonexistentEntityException("The mudancaCurso with id " + id + " no longer exists.");
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
            MudancaCurso mudancaCurso;
            try {
                mudancaCurso = em.getReference(MudancaCurso.class, id);
                mudancaCurso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mudancaCurso with id " + id + " no longer exists.", enfe);
            }
            Curso cursoDestino = mudancaCurso.getCursoDestino();
            if (cursoDestino != null) {
                cursoDestino.getMudancaCursoList().remove(mudancaCurso);
                cursoDestino = em.merge(cursoDestino);
            }
            Curso cusoOrigem = mudancaCurso.getCusoOrigem();
            if (cusoOrigem != null) {
                cusoOrigem.getMudancaCursoList().remove(mudancaCurso);
                cusoOrigem = em.merge(cusoOrigem);
            }
            Pedido pedido = mudancaCurso.getPedido();
            if (pedido != null) {
                pedido.getMudancaCursoList().remove(mudancaCurso);
                pedido = em.merge(pedido);
            }
            em.remove(mudancaCurso);
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
    public List<MudancaCurso> findMudancaCursoEntities() {
        return findMudancaCursoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<MudancaCurso> findMudancaCursoEntities(int maxResults, int firstResult) {
        return findMudancaCursoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<MudancaCurso> findMudancaCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MudancaCurso.class));
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
    public MudancaCurso findMudancaCurso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MudancaCurso.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getMudancaCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MudancaCurso> rt = cq.from(MudancaCurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

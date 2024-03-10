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
import modelo.Movimento;
import modelo.Pedido;
import modelo.TipoMovimento;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class MovimentoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public MovimentoJpaController(EntityManagerFactory emf) {
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
     * @param movimento
     */
    public void create(Movimento movimento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = movimento.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                movimento.setPedido(pedido);
            }
            TipoMovimento tipoMovimento = movimento.getTipoMovimento();
            if (tipoMovimento != null) {
                tipoMovimento = em.getReference(tipoMovimento.getClass(), tipoMovimento.getId());
                movimento.setTipoMovimento(tipoMovimento);
            }
            Utilizador utilizador = movimento.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getId());
                movimento.setUtilizador(utilizador);
            }
            em.persist(movimento);
            if (pedido != null) {
                pedido.getMovimentoList().add(movimento);
                pedido = em.merge(pedido);
            }
            if (tipoMovimento != null) {
                tipoMovimento.getMovimentoList().add(movimento);
                tipoMovimento = em.merge(tipoMovimento);
            }
            if (utilizador != null) {
                utilizador.getMovimentoList().add(movimento);
                utilizador = em.merge(utilizador);
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
     * @param movimento
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Movimento movimento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimento persistentMovimento = em.find(Movimento.class, movimento.getId());
            Pedido pedidoOld = persistentMovimento.getPedido();
            Pedido pedidoNew = movimento.getPedido();
            TipoMovimento tipoMovimentoOld = persistentMovimento.getTipoMovimento();
            TipoMovimento tipoMovimentoNew = movimento.getTipoMovimento();
            Utilizador utilizadorOld = persistentMovimento.getUtilizador();
            Utilizador utilizadorNew = movimento.getUtilizador();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                movimento.setPedido(pedidoNew);
            }
            if (tipoMovimentoNew != null) {
                tipoMovimentoNew = em.getReference(tipoMovimentoNew.getClass(), tipoMovimentoNew.getId());
                movimento.setTipoMovimento(tipoMovimentoNew);
            }
            if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getId());
                movimento.setUtilizador(utilizadorNew);
            }
            movimento = em.merge(movimento);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getMovimentoList().remove(movimento);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getMovimentoList().add(movimento);
                pedidoNew = em.merge(pedidoNew);
            }
            if (tipoMovimentoOld != null && !tipoMovimentoOld.equals(tipoMovimentoNew)) {
                tipoMovimentoOld.getMovimentoList().remove(movimento);
                tipoMovimentoOld = em.merge(tipoMovimentoOld);
            }
            if (tipoMovimentoNew != null && !tipoMovimentoNew.equals(tipoMovimentoOld)) {
                tipoMovimentoNew.getMovimentoList().add(movimento);
                tipoMovimentoNew = em.merge(tipoMovimentoNew);
            }
            if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getMovimentoList().remove(movimento);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getMovimentoList().add(movimento);
                utilizadorNew = em.merge(utilizadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = movimento.getId();
                if (findMovimento(id) == null) {
                    throw new NonexistentEntityException("The movimento with id " + id + " no longer exists.");
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
            Movimento movimento;
            try {
                movimento = em.getReference(Movimento.class, id);
                movimento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimento with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = movimento.getPedido();
            if (pedido != null) {
                pedido.getMovimentoList().remove(movimento);
                pedido = em.merge(pedido);
            }
            TipoMovimento tipoMovimento = movimento.getTipoMovimento();
            if (tipoMovimento != null) {
                tipoMovimento.getMovimentoList().remove(movimento);
                tipoMovimento = em.merge(tipoMovimento);
            }
            Utilizador utilizador = movimento.getUtilizador();
            if (utilizador != null) {
                utilizador.getMovimentoList().remove(movimento);
                utilizador = em.merge(utilizador);
            }
            em.remove(movimento);
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
    public List<Movimento> findMovimentoEntities() {
        return findMovimentoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Movimento> findMovimentoEntities(int maxResults, int firstResult) {
        return findMovimentoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<Movimento> findMovimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movimento.class));
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
    public Movimento findMovimento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimento.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getMovimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movimento> rt = cq.from(Movimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

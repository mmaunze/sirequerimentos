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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Declaracao;
import modelo.Pedido;
import modelo.TipoDelacarao;

/**
 *
 * @author Meldo Maunze
 */
public class DeclaracaoJpaController implements Serializable {

    public DeclaracaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Declaracao declaracao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = declaracao.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId());
                declaracao.setPedido(pedido);
            }
            TipoDelacarao tipoDelacarao = declaracao.getTipoDelacarao();
            if (tipoDelacarao != null) {
                tipoDelacarao = em.getReference(tipoDelacarao.getClass(), tipoDelacarao.getId());
                declaracao.setTipoDelacarao(tipoDelacarao);
            }
            em.persist(declaracao);
            if (pedido != null) {
                pedido.getDeclaracaoList().add(declaracao);
                pedido = em.merge(pedido);
            }
            if (tipoDelacarao != null) {
                tipoDelacarao.getDeclaracaoList().add(declaracao);
                tipoDelacarao = em.merge(tipoDelacarao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Declaracao declaracao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Declaracao persistentDeclaracao = em.find(Declaracao.class, declaracao.getId());
            Pedido pedidoOld = persistentDeclaracao.getPedido();
            Pedido pedidoNew = declaracao.getPedido();
            TipoDelacarao tipoDelacaraoOld = persistentDeclaracao.getTipoDelacarao();
            TipoDelacarao tipoDelacaraoNew = declaracao.getTipoDelacarao();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId());
                declaracao.setPedido(pedidoNew);
            }
            if (tipoDelacaraoNew != null) {
                tipoDelacaraoNew = em.getReference(tipoDelacaraoNew.getClass(), tipoDelacaraoNew.getId());
                declaracao.setTipoDelacarao(tipoDelacaraoNew);
            }
            declaracao = em.merge(declaracao);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getDeclaracaoList().remove(declaracao);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getDeclaracaoList().add(declaracao);
                pedidoNew = em.merge(pedidoNew);
            }
            if (tipoDelacaraoOld != null && !tipoDelacaraoOld.equals(tipoDelacaraoNew)) {
                tipoDelacaraoOld.getDeclaracaoList().remove(declaracao);
                tipoDelacaraoOld = em.merge(tipoDelacaraoOld);
            }
            if (tipoDelacaraoNew != null && !tipoDelacaraoNew.equals(tipoDelacaraoOld)) {
                tipoDelacaraoNew.getDeclaracaoList().add(declaracao);
                tipoDelacaraoNew = em.merge(tipoDelacaraoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = declaracao.getId();
                if (findDeclaracao(id) == null) {
                    throw new NonexistentEntityException("The declaracao with id " + id + " no longer exists.");
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
            Declaracao declaracao;
            try {
                declaracao = em.getReference(Declaracao.class, id);
                declaracao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The declaracao with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = declaracao.getPedido();
            if (pedido != null) {
                pedido.getDeclaracaoList().remove(declaracao);
                pedido = em.merge(pedido);
            }
            TipoDelacarao tipoDelacarao = declaracao.getTipoDelacarao();
            if (tipoDelacarao != null) {
                tipoDelacarao.getDeclaracaoList().remove(declaracao);
                tipoDelacarao = em.merge(tipoDelacarao);
            }
            em.remove(declaracao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Declaracao> findDeclaracaoEntities() {
        return findDeclaracaoEntities(true, -1, -1);
    }

    public List<Declaracao> findDeclaracaoEntities(int maxResults, int firstResult) {
        return findDeclaracaoEntities(false, maxResults, firstResult);
    }

    private List<Declaracao> findDeclaracaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Declaracao.class));
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

    public Declaracao findDeclaracao(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Declaracao.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeclaracaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Declaracao> rt = cq.from(Declaracao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

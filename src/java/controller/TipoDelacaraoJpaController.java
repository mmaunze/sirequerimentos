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
import modelo.Declaracao;
import modelo.TipoDelacarao;

/**
 *
 * @author Meldo Maunze
 */
public class TipoDelacaraoJpaController implements Serializable {


    /**
     *
     */
    private EntityManagerFactory emf = null;
    /**
     *
     * @param emf
     */
    public TipoDelacaraoJpaController(EntityManagerFactory emf) {
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
     * @param tipoDelacarao
     */
    public void create(TipoDelacarao tipoDelacarao) {
        if (tipoDelacarao.getDeclaracaoList() == null) {
            tipoDelacarao.setDeclaracaoList(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Declaracao> attachedDeclaracaoList = new ArrayList<>();
            for (Declaracao declaracaoListDeclaracaoToAttach : tipoDelacarao.getDeclaracaoList()) {
                declaracaoListDeclaracaoToAttach = em.getReference(declaracaoListDeclaracaoToAttach.getClass(), declaracaoListDeclaracaoToAttach.getId());
                attachedDeclaracaoList.add(declaracaoListDeclaracaoToAttach);
            }
            tipoDelacarao.setDeclaracaoList(attachedDeclaracaoList);
            em.persist(tipoDelacarao);
            for (Declaracao declaracaoListDeclaracao : tipoDelacarao.getDeclaracaoList()) {
                TipoDelacarao oldTipoDelacaraoOfDeclaracaoListDeclaracao = declaracaoListDeclaracao.getTipoDelacarao();
                declaracaoListDeclaracao.setTipoDelacarao(tipoDelacarao);
                declaracaoListDeclaracao = em.merge(declaracaoListDeclaracao);
                if (oldTipoDelacaraoOfDeclaracaoListDeclaracao != null) {
                    oldTipoDelacaraoOfDeclaracaoListDeclaracao.getDeclaracaoList().remove(declaracaoListDeclaracao);
                    oldTipoDelacaraoOfDeclaracaoListDeclaracao = em.merge(oldTipoDelacaraoOfDeclaracaoListDeclaracao);
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
     * @param tipoDelacarao
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(TipoDelacarao tipoDelacarao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDelacarao persistentTipoDelacarao = em.find(TipoDelacarao.class, tipoDelacarao.getId());
            List<Declaracao> declaracaoListOld = persistentTipoDelacarao.getDeclaracaoList();
            List<Declaracao> declaracaoListNew = tipoDelacarao.getDeclaracaoList();
            List<String> illegalOrphanMessages = null;
            for (Declaracao declaracaoListOldDeclaracao : declaracaoListOld) {
                if (!declaracaoListNew.contains(declaracaoListOldDeclaracao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Declaracao " + declaracaoListOldDeclaracao + " since its tipoDelacarao field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Declaracao> attachedDeclaracaoListNew = new ArrayList<>();
            for (Declaracao declaracaoListNewDeclaracaoToAttach : declaracaoListNew) {
                declaracaoListNewDeclaracaoToAttach = em.getReference(declaracaoListNewDeclaracaoToAttach.getClass(), declaracaoListNewDeclaracaoToAttach.getId());
                attachedDeclaracaoListNew.add(declaracaoListNewDeclaracaoToAttach);
            }
            declaracaoListNew = attachedDeclaracaoListNew;
            tipoDelacarao.setDeclaracaoList(declaracaoListNew);
            tipoDelacarao = em.merge(tipoDelacarao);
            for (Declaracao declaracaoListNewDeclaracao : declaracaoListNew) {
                if (!declaracaoListOld.contains(declaracaoListNewDeclaracao)) {
                    TipoDelacarao oldTipoDelacaraoOfDeclaracaoListNewDeclaracao = declaracaoListNewDeclaracao.getTipoDelacarao();
                    declaracaoListNewDeclaracao.setTipoDelacarao(tipoDelacarao);
                    declaracaoListNewDeclaracao = em.merge(declaracaoListNewDeclaracao);
                    if (oldTipoDelacaraoOfDeclaracaoListNewDeclaracao != null && !oldTipoDelacaraoOfDeclaracaoListNewDeclaracao.equals(tipoDelacarao)) {
                        oldTipoDelacaraoOfDeclaracaoListNewDeclaracao.getDeclaracaoList().remove(declaracaoListNewDeclaracao);
                        oldTipoDelacaraoOfDeclaracaoListNewDeclaracao = em.merge(oldTipoDelacaraoOfDeclaracaoListNewDeclaracao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoDelacarao.getId();
                if (findTipoDelacarao(id) == null) {
                    throw new NonexistentEntityException("The tipoDelacarao with id " + id + " no longer exists.");
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
            TipoDelacarao tipoDelacarao;
            try {
                tipoDelacarao = em.getReference(TipoDelacarao.class, id);
                tipoDelacarao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDelacarao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Declaracao> declaracaoListOrphanCheck = tipoDelacarao.getDeclaracaoList();
            for (Declaracao declaracaoListOrphanCheckDeclaracao : declaracaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TipoDelacarao (" + tipoDelacarao + ") cannot be destroyed since the Declaracao " + declaracaoListOrphanCheckDeclaracao + " in its declaracaoList field has a non-nullable tipoDelacarao field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoDelacarao);
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
    public List<TipoDelacarao> findTipoDelacaraoEntities() {
        return findTipoDelacaraoEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<TipoDelacarao> findTipoDelacaraoEntities(int maxResults, int firstResult) {
        return findTipoDelacaraoEntities(false, maxResults, firstResult);
    }

    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<TipoDelacarao> findTipoDelacaraoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDelacarao.class));
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
    public TipoDelacarao findTipoDelacarao(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDelacarao.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getTipoDelacaraoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDelacarao> rt = cq.from(TipoDelacarao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

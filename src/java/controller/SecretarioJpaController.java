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
import modelo.Faculdade;
import modelo.Utilizador;
import modelo.TratamentoPedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Secretario;

/**
 *
 * @author Meldo Maunze
 */
public class SecretarioJpaController implements Serializable {

    public SecretarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secretario secretario) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (secretario.getTratamentoPedidoList() == null) {
            secretario.setTratamentoPedidoList(new ArrayList<TratamentoPedido>());
        }
        List<String> illegalOrphanMessages = null;
        Utilizador utilizador1OrphanCheck = secretario.getUtilizador1();
        if (utilizador1OrphanCheck != null) {
            Secretario oldSecretarioOfUtilizador1 = utilizador1OrphanCheck.getSecretario();
            if (oldSecretarioOfUtilizador1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Utilizador " + utilizador1OrphanCheck + " already has an item of type Secretario whose utilizador1 column cannot be null. Please make another selection for the utilizador1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faculdade faculdade = secretario.getFaculdade();
            if (faculdade != null) {
                faculdade = em.getReference(faculdade.getClass(), faculdade.getId());
                secretario.setFaculdade(faculdade);
            }
            Utilizador utilizador1 = secretario.getUtilizador1();
            if (utilizador1 != null) {
                utilizador1 = em.getReference(utilizador1.getClass(), utilizador1.getId());
                secretario.setUtilizador1(utilizador1);
            }
            List<TratamentoPedido> attachedTratamentoPedidoList = new ArrayList<TratamentoPedido>();
            for (TratamentoPedido tratamentoPedidoListTratamentoPedidoToAttach : secretario.getTratamentoPedidoList()) {
                tratamentoPedidoListTratamentoPedidoToAttach = em.getReference(tratamentoPedidoListTratamentoPedidoToAttach.getClass(), tratamentoPedidoListTratamentoPedidoToAttach.getId());
                attachedTratamentoPedidoList.add(tratamentoPedidoListTratamentoPedidoToAttach);
            }
            secretario.setTratamentoPedidoList(attachedTratamentoPedidoList);
            em.persist(secretario);
            if (faculdade != null) {
                faculdade.getSecretarioList().add(secretario);
                faculdade = em.merge(faculdade);
            }
            if (utilizador1 != null) {
                utilizador1.setSecretario(secretario);
                utilizador1 = em.merge(utilizador1);
            }
            for (TratamentoPedido tratamentoPedidoListTratamentoPedido : secretario.getTratamentoPedidoList()) {
                Secretario oldSecretarioOfTratamentoPedidoListTratamentoPedido = tratamentoPedidoListTratamentoPedido.getSecretario();
                tratamentoPedidoListTratamentoPedido.setSecretario(secretario);
                tratamentoPedidoListTratamentoPedido = em.merge(tratamentoPedidoListTratamentoPedido);
                if (oldSecretarioOfTratamentoPedidoListTratamentoPedido != null) {
                    oldSecretarioOfTratamentoPedidoListTratamentoPedido.getTratamentoPedidoList().remove(tratamentoPedidoListTratamentoPedido);
                    oldSecretarioOfTratamentoPedidoListTratamentoPedido = em.merge(oldSecretarioOfTratamentoPedidoListTratamentoPedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSecretario(secretario.getUtilizador()) != null) {
                throw new PreexistingEntityException("Secretario " + secretario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secretario secretario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretario persistentSecretario = em.find(Secretario.class, secretario.getUtilizador());
            Faculdade faculdadeOld = persistentSecretario.getFaculdade();
            Faculdade faculdadeNew = secretario.getFaculdade();
            Utilizador utilizador1Old = persistentSecretario.getUtilizador1();
            Utilizador utilizador1New = secretario.getUtilizador1();
            List<TratamentoPedido> tratamentoPedidoListOld = persistentSecretario.getTratamentoPedidoList();
            List<TratamentoPedido> tratamentoPedidoListNew = secretario.getTratamentoPedidoList();
            List<String> illegalOrphanMessages = null;
            if (utilizador1New != null && !utilizador1New.equals(utilizador1Old)) {
                Secretario oldSecretarioOfUtilizador1 = utilizador1New.getSecretario();
                if (oldSecretarioOfUtilizador1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Utilizador " + utilizador1New + " already has an item of type Secretario whose utilizador1 column cannot be null. Please make another selection for the utilizador1 field.");
                }
            }
            for (TratamentoPedido tratamentoPedidoListOldTratamentoPedido : tratamentoPedidoListOld) {
                if (!tratamentoPedidoListNew.contains(tratamentoPedidoListOldTratamentoPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TratamentoPedido " + tratamentoPedidoListOldTratamentoPedido + " since its secretario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (faculdadeNew != null) {
                faculdadeNew = em.getReference(faculdadeNew.getClass(), faculdadeNew.getId());
                secretario.setFaculdade(faculdadeNew);
            }
            if (utilizador1New != null) {
                utilizador1New = em.getReference(utilizador1New.getClass(), utilizador1New.getId());
                secretario.setUtilizador1(utilizador1New);
            }
            List<TratamentoPedido> attachedTratamentoPedidoListNew = new ArrayList<TratamentoPedido>();
            for (TratamentoPedido tratamentoPedidoListNewTratamentoPedidoToAttach : tratamentoPedidoListNew) {
                tratamentoPedidoListNewTratamentoPedidoToAttach = em.getReference(tratamentoPedidoListNewTratamentoPedidoToAttach.getClass(), tratamentoPedidoListNewTratamentoPedidoToAttach.getId());
                attachedTratamentoPedidoListNew.add(tratamentoPedidoListNewTratamentoPedidoToAttach);
            }
            tratamentoPedidoListNew = attachedTratamentoPedidoListNew;
            secretario.setTratamentoPedidoList(tratamentoPedidoListNew);
            secretario = em.merge(secretario);
            if (faculdadeOld != null && !faculdadeOld.equals(faculdadeNew)) {
                faculdadeOld.getSecretarioList().remove(secretario);
                faculdadeOld = em.merge(faculdadeOld);
            }
            if (faculdadeNew != null && !faculdadeNew.equals(faculdadeOld)) {
                faculdadeNew.getSecretarioList().add(secretario);
                faculdadeNew = em.merge(faculdadeNew);
            }
            if (utilizador1Old != null && !utilizador1Old.equals(utilizador1New)) {
                utilizador1Old.setSecretario(null);
                utilizador1Old = em.merge(utilizador1Old);
            }
            if (utilizador1New != null && !utilizador1New.equals(utilizador1Old)) {
                utilizador1New.setSecretario(secretario);
                utilizador1New = em.merge(utilizador1New);
            }
            for (TratamentoPedido tratamentoPedidoListNewTratamentoPedido : tratamentoPedidoListNew) {
                if (!tratamentoPedidoListOld.contains(tratamentoPedidoListNewTratamentoPedido)) {
                    Secretario oldSecretarioOfTratamentoPedidoListNewTratamentoPedido = tratamentoPedidoListNewTratamentoPedido.getSecretario();
                    tratamentoPedidoListNewTratamentoPedido.setSecretario(secretario);
                    tratamentoPedidoListNewTratamentoPedido = em.merge(tratamentoPedidoListNewTratamentoPedido);
                    if (oldSecretarioOfTratamentoPedidoListNewTratamentoPedido != null && !oldSecretarioOfTratamentoPedidoListNewTratamentoPedido.equals(secretario)) {
                        oldSecretarioOfTratamentoPedidoListNewTratamentoPedido.getTratamentoPedidoList().remove(tratamentoPedidoListNewTratamentoPedido);
                        oldSecretarioOfTratamentoPedidoListNewTratamentoPedido = em.merge(oldSecretarioOfTratamentoPedidoListNewTratamentoPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = secretario.getUtilizador();
                if (findSecretario(id) == null) {
                    throw new NonexistentEntityException("The secretario with id " + id + " no longer exists.");
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
            Secretario secretario;
            try {
                secretario = em.getReference(Secretario.class, id);
                secretario.getUtilizador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secretario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TratamentoPedido> tratamentoPedidoListOrphanCheck = secretario.getTratamentoPedidoList();
            for (TratamentoPedido tratamentoPedidoListOrphanCheckTratamentoPedido : tratamentoPedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Secretario (" + secretario + ") cannot be destroyed since the TratamentoPedido " + tratamentoPedidoListOrphanCheckTratamentoPedido + " in its tratamentoPedidoList field has a non-nullable secretario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Faculdade faculdade = secretario.getFaculdade();
            if (faculdade != null) {
                faculdade.getSecretarioList().remove(secretario);
                faculdade = em.merge(faculdade);
            }
            Utilizador utilizador1 = secretario.getUtilizador1();
            if (utilizador1 != null) {
                utilizador1.setSecretario(null);
                utilizador1 = em.merge(utilizador1);
            }
            em.remove(secretario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secretario> findSecretarioEntities() {
        return findSecretarioEntities(true, -1, -1);
    }

    public List<Secretario> findSecretarioEntities(int maxResults, int firstResult) {
        return findSecretarioEntities(false, maxResults, firstResult);
    }

    private List<Secretario> findSecretarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secretario.class));
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

    public Secretario findSecretario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secretario.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecretarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secretario> rt = cq.from(Secretario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

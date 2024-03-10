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
import modelo.Secretario;
import modelo.Cta;
import modelo.Departamento;
import modelo.TipoUtilizador;
import modelo.Estudante;
import modelo.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Movimento;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class UtilizadorJpaController implements Serializable {

    public UtilizadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Utilizador utilizador) throws PreexistingEntityException, Exception {
        if (utilizador.getPedidoList() == null) {
            utilizador.setPedidoList(new ArrayList<Pedido>());
        }
        if (utilizador.getMovimentoList() == null) {
            utilizador.setMovimentoList(new ArrayList<Movimento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretario secretario = utilizador.getSecretario();
            if (secretario != null) {
                secretario = em.getReference(secretario.getClass(), secretario.getUtilizador());
                utilizador.setSecretario(secretario);
            }
            Cta cta = utilizador.getCta();
            if (cta != null) {
                cta = em.getReference(cta.getClass(), cta.getUtilizador());
                utilizador.setCta(cta);
            }
            Departamento departamento = utilizador.getDepartamento();
            if (departamento != null) {
                departamento = em.getReference(departamento.getClass(), departamento.getId());
                utilizador.setDepartamento(departamento);
            }
            TipoUtilizador tipoUtilizador = utilizador.getTipoUtilizador();
            if (tipoUtilizador != null) {
                tipoUtilizador = em.getReference(tipoUtilizador.getClass(), tipoUtilizador.getId());
                utilizador.setTipoUtilizador(tipoUtilizador);
            }
            Estudante estudante = utilizador.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getUtilizador());
                utilizador.setEstudante(estudante);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : utilizador.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getId());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            utilizador.setPedidoList(attachedPedidoList);
            List<Movimento> attachedMovimentoList = new ArrayList<Movimento>();
            for (Movimento movimentoListMovimentoToAttach : utilizador.getMovimentoList()) {
                movimentoListMovimentoToAttach = em.getReference(movimentoListMovimentoToAttach.getClass(), movimentoListMovimentoToAttach.getId());
                attachedMovimentoList.add(movimentoListMovimentoToAttach);
            }
            utilizador.setMovimentoList(attachedMovimentoList);
            em.persist(utilizador);
            if (secretario != null) {
                Utilizador oldUtilizador1OfSecretario = secretario.getUtilizador1();
                if (oldUtilizador1OfSecretario != null) {
                    oldUtilizador1OfSecretario.setSecretario(null);
                    oldUtilizador1OfSecretario = em.merge(oldUtilizador1OfSecretario);
                }
                secretario.setUtilizador1(utilizador);
                secretario = em.merge(secretario);
            }
            if (cta != null) {
                Utilizador oldUtilizador1OfCta = cta.getUtilizador1();
                if (oldUtilizador1OfCta != null) {
                    oldUtilizador1OfCta.setCta(null);
                    oldUtilizador1OfCta = em.merge(oldUtilizador1OfCta);
                }
                cta.setUtilizador1(utilizador);
                cta = em.merge(cta);
            }
            if (departamento != null) {
                departamento.getUtilizadorList().add(utilizador);
                departamento = em.merge(departamento);
            }
            if (tipoUtilizador != null) {
                tipoUtilizador.getUtilizadorList().add(utilizador);
                tipoUtilizador = em.merge(tipoUtilizador);
            }
            if (estudante != null) {
                Utilizador oldUtilizador1OfEstudante = estudante.getUtilizador1();
                if (oldUtilizador1OfEstudante != null) {
                    oldUtilizador1OfEstudante.setEstudante(null);
                    oldUtilizador1OfEstudante = em.merge(oldUtilizador1OfEstudante);
                }
                estudante.setUtilizador1(utilizador);
                estudante = em.merge(estudante);
            }
            for (Pedido pedidoListPedido : utilizador.getPedidoList()) {
                Utilizador oldUtilizadorOfPedidoListPedido = pedidoListPedido.getUtilizador();
                pedidoListPedido.setUtilizador(utilizador);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldUtilizadorOfPedidoListPedido != null) {
                    oldUtilizadorOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldUtilizadorOfPedidoListPedido = em.merge(oldUtilizadorOfPedidoListPedido);
                }
            }
            for (Movimento movimentoListMovimento : utilizador.getMovimentoList()) {
                Utilizador oldUtilizadorOfMovimentoListMovimento = movimentoListMovimento.getUtilizador();
                movimentoListMovimento.setUtilizador(utilizador);
                movimentoListMovimento = em.merge(movimentoListMovimento);
                if (oldUtilizadorOfMovimentoListMovimento != null) {
                    oldUtilizadorOfMovimentoListMovimento.getMovimentoList().remove(movimentoListMovimento);
                    oldUtilizadorOfMovimentoListMovimento = em.merge(oldUtilizadorOfMovimentoListMovimento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUtilizador(utilizador.getId()) != null) {
                throw new PreexistingEntityException("Utilizador " + utilizador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Utilizador utilizador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilizador persistentUtilizador = em.find(Utilizador.class, utilizador.getId());
            Secretario secretarioOld = persistentUtilizador.getSecretario();
            Secretario secretarioNew = utilizador.getSecretario();
            Cta ctaOld = persistentUtilizador.getCta();
            Cta ctaNew = utilizador.getCta();
            Departamento departamentoOld = persistentUtilizador.getDepartamento();
            Departamento departamentoNew = utilizador.getDepartamento();
            TipoUtilizador tipoUtilizadorOld = persistentUtilizador.getTipoUtilizador();
            TipoUtilizador tipoUtilizadorNew = utilizador.getTipoUtilizador();
            Estudante estudanteOld = persistentUtilizador.getEstudante();
            Estudante estudanteNew = utilizador.getEstudante();
            List<Pedido> pedidoListOld = persistentUtilizador.getPedidoList();
            List<Pedido> pedidoListNew = utilizador.getPedidoList();
            List<Movimento> movimentoListOld = persistentUtilizador.getMovimentoList();
            List<Movimento> movimentoListNew = utilizador.getMovimentoList();
            List<String> illegalOrphanMessages = null;
            if (secretarioOld != null && !secretarioOld.equals(secretarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Secretario " + secretarioOld + " since its utilizador1 field is not nullable.");
            }
            if (ctaOld != null && !ctaOld.equals(ctaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cta " + ctaOld + " since its utilizador1 field is not nullable.");
            }
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Estudante " + estudanteOld + " since its utilizador1 field is not nullable.");
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its utilizador field is not nullable.");
                }
            }
            for (Movimento movimentoListOldMovimento : movimentoListOld) {
                if (!movimentoListNew.contains(movimentoListOldMovimento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Movimento " + movimentoListOldMovimento + " since its utilizador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (secretarioNew != null) {
                secretarioNew = em.getReference(secretarioNew.getClass(), secretarioNew.getUtilizador());
                utilizador.setSecretario(secretarioNew);
            }
            if (ctaNew != null) {
                ctaNew = em.getReference(ctaNew.getClass(), ctaNew.getUtilizador());
                utilizador.setCta(ctaNew);
            }
            if (departamentoNew != null) {
                departamentoNew = em.getReference(departamentoNew.getClass(), departamentoNew.getId());
                utilizador.setDepartamento(departamentoNew);
            }
            if (tipoUtilizadorNew != null) {
                tipoUtilizadorNew = em.getReference(tipoUtilizadorNew.getClass(), tipoUtilizadorNew.getId());
                utilizador.setTipoUtilizador(tipoUtilizadorNew);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getUtilizador());
                utilizador.setEstudante(estudanteNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getId());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            utilizador.setPedidoList(pedidoListNew);
            List<Movimento> attachedMovimentoListNew = new ArrayList<Movimento>();
            for (Movimento movimentoListNewMovimentoToAttach : movimentoListNew) {
                movimentoListNewMovimentoToAttach = em.getReference(movimentoListNewMovimentoToAttach.getClass(), movimentoListNewMovimentoToAttach.getId());
                attachedMovimentoListNew.add(movimentoListNewMovimentoToAttach);
            }
            movimentoListNew = attachedMovimentoListNew;
            utilizador.setMovimentoList(movimentoListNew);
            utilizador = em.merge(utilizador);
            if (secretarioNew != null && !secretarioNew.equals(secretarioOld)) {
                Utilizador oldUtilizador1OfSecretario = secretarioNew.getUtilizador1();
                if (oldUtilizador1OfSecretario != null) {
                    oldUtilizador1OfSecretario.setSecretario(null);
                    oldUtilizador1OfSecretario = em.merge(oldUtilizador1OfSecretario);
                }
                secretarioNew.setUtilizador1(utilizador);
                secretarioNew = em.merge(secretarioNew);
            }
            if (ctaNew != null && !ctaNew.equals(ctaOld)) {
                Utilizador oldUtilizador1OfCta = ctaNew.getUtilizador1();
                if (oldUtilizador1OfCta != null) {
                    oldUtilizador1OfCta.setCta(null);
                    oldUtilizador1OfCta = em.merge(oldUtilizador1OfCta);
                }
                ctaNew.setUtilizador1(utilizador);
                ctaNew = em.merge(ctaNew);
            }
            if (departamentoOld != null && !departamentoOld.equals(departamentoNew)) {
                departamentoOld.getUtilizadorList().remove(utilizador);
                departamentoOld = em.merge(departamentoOld);
            }
            if (departamentoNew != null && !departamentoNew.equals(departamentoOld)) {
                departamentoNew.getUtilizadorList().add(utilizador);
                departamentoNew = em.merge(departamentoNew);
            }
            if (tipoUtilizadorOld != null && !tipoUtilizadorOld.equals(tipoUtilizadorNew)) {
                tipoUtilizadorOld.getUtilizadorList().remove(utilizador);
                tipoUtilizadorOld = em.merge(tipoUtilizadorOld);
            }
            if (tipoUtilizadorNew != null && !tipoUtilizadorNew.equals(tipoUtilizadorOld)) {
                tipoUtilizadorNew.getUtilizadorList().add(utilizador);
                tipoUtilizadorNew = em.merge(tipoUtilizadorNew);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                Utilizador oldUtilizador1OfEstudante = estudanteNew.getUtilizador1();
                if (oldUtilizador1OfEstudante != null) {
                    oldUtilizador1OfEstudante.setEstudante(null);
                    oldUtilizador1OfEstudante = em.merge(oldUtilizador1OfEstudante);
                }
                estudanteNew.setUtilizador1(utilizador);
                estudanteNew = em.merge(estudanteNew);
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Utilizador oldUtilizadorOfPedidoListNewPedido = pedidoListNewPedido.getUtilizador();
                    pedidoListNewPedido.setUtilizador(utilizador);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldUtilizadorOfPedidoListNewPedido != null && !oldUtilizadorOfPedidoListNewPedido.equals(utilizador)) {
                        oldUtilizadorOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldUtilizadorOfPedidoListNewPedido = em.merge(oldUtilizadorOfPedidoListNewPedido);
                    }
                }
            }
            for (Movimento movimentoListNewMovimento : movimentoListNew) {
                if (!movimentoListOld.contains(movimentoListNewMovimento)) {
                    Utilizador oldUtilizadorOfMovimentoListNewMovimento = movimentoListNewMovimento.getUtilizador();
                    movimentoListNewMovimento.setUtilizador(utilizador);
                    movimentoListNewMovimento = em.merge(movimentoListNewMovimento);
                    if (oldUtilizadorOfMovimentoListNewMovimento != null && !oldUtilizadorOfMovimentoListNewMovimento.equals(utilizador)) {
                        oldUtilizadorOfMovimentoListNewMovimento.getMovimentoList().remove(movimentoListNewMovimento);
                        oldUtilizadorOfMovimentoListNewMovimento = em.merge(oldUtilizadorOfMovimentoListNewMovimento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = utilizador.getId();
                if (findUtilizador(id) == null) {
                    throw new NonexistentEntityException("The utilizador with id " + id + " no longer exists.");
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
            Utilizador utilizador;
            try {
                utilizador = em.getReference(Utilizador.class, id);
                utilizador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utilizador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Secretario secretarioOrphanCheck = utilizador.getSecretario();
            if (secretarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilizador (" + utilizador + ") cannot be destroyed since the Secretario " + secretarioOrphanCheck + " in its secretario field has a non-nullable utilizador1 field.");
            }
            Cta ctaOrphanCheck = utilizador.getCta();
            if (ctaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilizador (" + utilizador + ") cannot be destroyed since the Cta " + ctaOrphanCheck + " in its cta field has a non-nullable utilizador1 field.");
            }
            Estudante estudanteOrphanCheck = utilizador.getEstudante();
            if (estudanteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilizador (" + utilizador + ") cannot be destroyed since the Estudante " + estudanteOrphanCheck + " in its estudante field has a non-nullable utilizador1 field.");
            }
            List<Pedido> pedidoListOrphanCheck = utilizador.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilizador (" + utilizador + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable utilizador field.");
            }
            List<Movimento> movimentoListOrphanCheck = utilizador.getMovimentoList();
            for (Movimento movimentoListOrphanCheckMovimento : movimentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilizador (" + utilizador + ") cannot be destroyed since the Movimento " + movimentoListOrphanCheckMovimento + " in its movimentoList field has a non-nullable utilizador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento departamento = utilizador.getDepartamento();
            if (departamento != null) {
                departamento.getUtilizadorList().remove(utilizador);
                departamento = em.merge(departamento);
            }
            TipoUtilizador tipoUtilizador = utilizador.getTipoUtilizador();
            if (tipoUtilizador != null) {
                tipoUtilizador.getUtilizadorList().remove(utilizador);
                tipoUtilizador = em.merge(tipoUtilizador);
            }
            em.remove(utilizador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Utilizador> findUtilizadorEntities() {
        return findUtilizadorEntities(true, -1, -1);
    }

    public List<Utilizador> findUtilizadorEntities(int maxResults, int firstResult) {
        return findUtilizadorEntities(false, maxResults, firstResult);
    }

    private List<Utilizador> findUtilizadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilizador.class));
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

    public Utilizador findUtilizador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilizador.class, id);
        } finally {
            em.close();
        }
    }

    public int getUtilizadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilizador> rt = cq.from(Utilizador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

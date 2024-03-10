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
import modelo.Estado;
import modelo.TipoPedido;
import modelo.Utilizador;
import modelo.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.RealizacaoExame;
import modelo.MudancaCurso;
import modelo.SituacaoDivida;
import modelo.TratamentoPedido;
import modelo.MatriculaForaEpoca;
import modelo.Movimento;
import modelo.PedidosCertificados;
import modelo.TransferenciaCrs;
import modelo.ReintegracaoAcademica;
import modelo.Equivalencia;
import modelo.SubmissaoRelatorioEstagio;
import modelo.SubmissaoMonografias;
import modelo.Declaracao;

/**
 *
 * @author Meldo Maunze
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        if (pedido.getPedidoList() == null) {
            pedido.setPedidoList(new ArrayList<Pedido>());
        }
        if (pedido.getPedidoList1() == null) {
            pedido.setPedidoList1(new ArrayList<Pedido>());
        }
        if (pedido.getPedidoList2() == null) {
            pedido.setPedidoList2(new ArrayList<Pedido>());
        }
        if (pedido.getPedidoList3() == null) {
            pedido.setPedidoList3(new ArrayList<Pedido>());
        }
        if (pedido.getPedidoList4() == null) {
            pedido.setPedidoList4(new ArrayList<Pedido>());
        }
        if (pedido.getPedidoList5() == null) {
            pedido.setPedidoList5(new ArrayList<Pedido>());
        }
        if (pedido.getPedidoList6() == null) {
            pedido.setPedidoList6(new ArrayList<Pedido>());
        }
        if (pedido.getPedidoList7() == null) {
            pedido.setPedidoList7(new ArrayList<Pedido>());
        }
        if (pedido.getRealizacaoExameList() == null) {
            pedido.setRealizacaoExameList(new ArrayList<RealizacaoExame>());
        }
        if (pedido.getMudancaCursoList() == null) {
            pedido.setMudancaCursoList(new ArrayList<MudancaCurso>());
        }
        if (pedido.getSituacaoDividaList() == null) {
            pedido.setSituacaoDividaList(new ArrayList<SituacaoDivida>());
        }
        if (pedido.getTratamentoPedidoList() == null) {
            pedido.setTratamentoPedidoList(new ArrayList<TratamentoPedido>());
        }
        if (pedido.getMatriculaForaEpocaList() == null) {
            pedido.setMatriculaForaEpocaList(new ArrayList<MatriculaForaEpoca>());
        }
        if (pedido.getMovimentoList() == null) {
            pedido.setMovimentoList(new ArrayList<Movimento>());
        }
        if (pedido.getPedidosCertificadosList() == null) {
            pedido.setPedidosCertificadosList(new ArrayList<PedidosCertificados>());
        }
        if (pedido.getTransferenciaCrsList() == null) {
            pedido.setTransferenciaCrsList(new ArrayList<TransferenciaCrs>());
        }
        if (pedido.getReintegracaoAcademicaList() == null) {
            pedido.setReintegracaoAcademicaList(new ArrayList<ReintegracaoAcademica>());
        }
        if (pedido.getEquivalenciaList() == null) {
            pedido.setEquivalenciaList(new ArrayList<Equivalencia>());
        }
        if (pedido.getSubmissaoRelatorioEstagioList() == null) {
            pedido.setSubmissaoRelatorioEstagioList(new ArrayList<SubmissaoRelatorioEstagio>());
        }
        if (pedido.getSubmissaoMonografiasList() == null) {
            pedido.setSubmissaoMonografiasList(new ArrayList<SubmissaoMonografias>());
        }
        if (pedido.getDeclaracaoList() == null) {
            pedido.setDeclaracaoList(new ArrayList<Declaracao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estado = pedido.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getId());
                pedido.setEstado(estado);
            }
            TipoPedido tipoPedido = pedido.getTipoPedido();
            if (tipoPedido != null) {
                tipoPedido = em.getReference(tipoPedido.getClass(), tipoPedido.getId());
                pedido.setTipoPedido(tipoPedido);
            }
            Utilizador utilizador = pedido.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getId());
                pedido.setUtilizador(utilizador);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : pedido.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getId());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            pedido.setPedidoList(attachedPedidoList);
            List<Pedido> attachedPedidoList1 = new ArrayList<Pedido>();
            for (Pedido pedidoList1PedidoToAttach : pedido.getPedidoList1()) {
                pedidoList1PedidoToAttach = em.getReference(pedidoList1PedidoToAttach.getClass(), pedidoList1PedidoToAttach.getId());
                attachedPedidoList1.add(pedidoList1PedidoToAttach);
            }
            pedido.setPedidoList1(attachedPedidoList1);
            List<Pedido> attachedPedidoList2 = new ArrayList<Pedido>();
            for (Pedido pedidoList2PedidoToAttach : pedido.getPedidoList2()) {
                pedidoList2PedidoToAttach = em.getReference(pedidoList2PedidoToAttach.getClass(), pedidoList2PedidoToAttach.getId());
                attachedPedidoList2.add(pedidoList2PedidoToAttach);
            }
            pedido.setPedidoList2(attachedPedidoList2);
            List<Pedido> attachedPedidoList3 = new ArrayList<Pedido>();
            for (Pedido pedidoList3PedidoToAttach : pedido.getPedidoList3()) {
                pedidoList3PedidoToAttach = em.getReference(pedidoList3PedidoToAttach.getClass(), pedidoList3PedidoToAttach.getId());
                attachedPedidoList3.add(pedidoList3PedidoToAttach);
            }
            pedido.setPedidoList3(attachedPedidoList3);
            List<Pedido> attachedPedidoList4 = new ArrayList<Pedido>();
            for (Pedido pedidoList4PedidoToAttach : pedido.getPedidoList4()) {
                pedidoList4PedidoToAttach = em.getReference(pedidoList4PedidoToAttach.getClass(), pedidoList4PedidoToAttach.getId());
                attachedPedidoList4.add(pedidoList4PedidoToAttach);
            }
            pedido.setPedidoList4(attachedPedidoList4);
            List<Pedido> attachedPedidoList5 = new ArrayList<Pedido>();
            for (Pedido pedidoList5PedidoToAttach : pedido.getPedidoList5()) {
                pedidoList5PedidoToAttach = em.getReference(pedidoList5PedidoToAttach.getClass(), pedidoList5PedidoToAttach.getId());
                attachedPedidoList5.add(pedidoList5PedidoToAttach);
            }
            pedido.setPedidoList5(attachedPedidoList5);
            List<Pedido> attachedPedidoList6 = new ArrayList<Pedido>();
            for (Pedido pedidoList6PedidoToAttach : pedido.getPedidoList6()) {
                pedidoList6PedidoToAttach = em.getReference(pedidoList6PedidoToAttach.getClass(), pedidoList6PedidoToAttach.getId());
                attachedPedidoList6.add(pedidoList6PedidoToAttach);
            }
            pedido.setPedidoList6(attachedPedidoList6);
            List<Pedido> attachedPedidoList7 = new ArrayList<Pedido>();
            for (Pedido pedidoList7PedidoToAttach : pedido.getPedidoList7()) {
                pedidoList7PedidoToAttach = em.getReference(pedidoList7PedidoToAttach.getClass(), pedidoList7PedidoToAttach.getId());
                attachedPedidoList7.add(pedidoList7PedidoToAttach);
            }
            pedido.setPedidoList7(attachedPedidoList7);
            List<RealizacaoExame> attachedRealizacaoExameList = new ArrayList<RealizacaoExame>();
            for (RealizacaoExame realizacaoExameListRealizacaoExameToAttach : pedido.getRealizacaoExameList()) {
                realizacaoExameListRealizacaoExameToAttach = em.getReference(realizacaoExameListRealizacaoExameToAttach.getClass(), realizacaoExameListRealizacaoExameToAttach.getId());
                attachedRealizacaoExameList.add(realizacaoExameListRealizacaoExameToAttach);
            }
            pedido.setRealizacaoExameList(attachedRealizacaoExameList);
            List<MudancaCurso> attachedMudancaCursoList = new ArrayList<MudancaCurso>();
            for (MudancaCurso mudancaCursoListMudancaCursoToAttach : pedido.getMudancaCursoList()) {
                mudancaCursoListMudancaCursoToAttach = em.getReference(mudancaCursoListMudancaCursoToAttach.getClass(), mudancaCursoListMudancaCursoToAttach.getId());
                attachedMudancaCursoList.add(mudancaCursoListMudancaCursoToAttach);
            }
            pedido.setMudancaCursoList(attachedMudancaCursoList);
            List<SituacaoDivida> attachedSituacaoDividaList = new ArrayList<SituacaoDivida>();
            for (SituacaoDivida situacaoDividaListSituacaoDividaToAttach : pedido.getSituacaoDividaList()) {
                situacaoDividaListSituacaoDividaToAttach = em.getReference(situacaoDividaListSituacaoDividaToAttach.getClass(), situacaoDividaListSituacaoDividaToAttach.getId());
                attachedSituacaoDividaList.add(situacaoDividaListSituacaoDividaToAttach);
            }
            pedido.setSituacaoDividaList(attachedSituacaoDividaList);
            List<TratamentoPedido> attachedTratamentoPedidoList = new ArrayList<TratamentoPedido>();
            for (TratamentoPedido tratamentoPedidoListTratamentoPedidoToAttach : pedido.getTratamentoPedidoList()) {
                tratamentoPedidoListTratamentoPedidoToAttach = em.getReference(tratamentoPedidoListTratamentoPedidoToAttach.getClass(), tratamentoPedidoListTratamentoPedidoToAttach.getId());
                attachedTratamentoPedidoList.add(tratamentoPedidoListTratamentoPedidoToAttach);
            }
            pedido.setTratamentoPedidoList(attachedTratamentoPedidoList);
            List<MatriculaForaEpoca> attachedMatriculaForaEpocaList = new ArrayList<MatriculaForaEpoca>();
            for (MatriculaForaEpoca matriculaForaEpocaListMatriculaForaEpocaToAttach : pedido.getMatriculaForaEpocaList()) {
                matriculaForaEpocaListMatriculaForaEpocaToAttach = em.getReference(matriculaForaEpocaListMatriculaForaEpocaToAttach.getClass(), matriculaForaEpocaListMatriculaForaEpocaToAttach.getId());
                attachedMatriculaForaEpocaList.add(matriculaForaEpocaListMatriculaForaEpocaToAttach);
            }
            pedido.setMatriculaForaEpocaList(attachedMatriculaForaEpocaList);
            List<Movimento> attachedMovimentoList = new ArrayList<Movimento>();
            for (Movimento movimentoListMovimentoToAttach : pedido.getMovimentoList()) {
                movimentoListMovimentoToAttach = em.getReference(movimentoListMovimentoToAttach.getClass(), movimentoListMovimentoToAttach.getId());
                attachedMovimentoList.add(movimentoListMovimentoToAttach);
            }
            pedido.setMovimentoList(attachedMovimentoList);
            List<PedidosCertificados> attachedPedidosCertificadosList = new ArrayList<PedidosCertificados>();
            for (PedidosCertificados pedidosCertificadosListPedidosCertificadosToAttach : pedido.getPedidosCertificadosList()) {
                pedidosCertificadosListPedidosCertificadosToAttach = em.getReference(pedidosCertificadosListPedidosCertificadosToAttach.getClass(), pedidosCertificadosListPedidosCertificadosToAttach.getId());
                attachedPedidosCertificadosList.add(pedidosCertificadosListPedidosCertificadosToAttach);
            }
            pedido.setPedidosCertificadosList(attachedPedidosCertificadosList);
            List<TransferenciaCrs> attachedTransferenciaCrsList = new ArrayList<TransferenciaCrs>();
            for (TransferenciaCrs transferenciaCrsListTransferenciaCrsToAttach : pedido.getTransferenciaCrsList()) {
                transferenciaCrsListTransferenciaCrsToAttach = em.getReference(transferenciaCrsListTransferenciaCrsToAttach.getClass(), transferenciaCrsListTransferenciaCrsToAttach.getId());
                attachedTransferenciaCrsList.add(transferenciaCrsListTransferenciaCrsToAttach);
            }
            pedido.setTransferenciaCrsList(attachedTransferenciaCrsList);
            List<ReintegracaoAcademica> attachedReintegracaoAcademicaList = new ArrayList<ReintegracaoAcademica>();
            for (ReintegracaoAcademica reintegracaoAcademicaListReintegracaoAcademicaToAttach : pedido.getReintegracaoAcademicaList()) {
                reintegracaoAcademicaListReintegracaoAcademicaToAttach = em.getReference(reintegracaoAcademicaListReintegracaoAcademicaToAttach.getClass(), reintegracaoAcademicaListReintegracaoAcademicaToAttach.getId());
                attachedReintegracaoAcademicaList.add(reintegracaoAcademicaListReintegracaoAcademicaToAttach);
            }
            pedido.setReintegracaoAcademicaList(attachedReintegracaoAcademicaList);
            List<Equivalencia> attachedEquivalenciaList = new ArrayList<Equivalencia>();
            for (Equivalencia equivalenciaListEquivalenciaToAttach : pedido.getEquivalenciaList()) {
                equivalenciaListEquivalenciaToAttach = em.getReference(equivalenciaListEquivalenciaToAttach.getClass(), equivalenciaListEquivalenciaToAttach.getId());
                attachedEquivalenciaList.add(equivalenciaListEquivalenciaToAttach);
            }
            pedido.setEquivalenciaList(attachedEquivalenciaList);
            List<SubmissaoRelatorioEstagio> attachedSubmissaoRelatorioEstagioList = new ArrayList<SubmissaoRelatorioEstagio>();
            for (SubmissaoRelatorioEstagio submissaoRelatorioEstagioListSubmissaoRelatorioEstagioToAttach : pedido.getSubmissaoRelatorioEstagioList()) {
                submissaoRelatorioEstagioListSubmissaoRelatorioEstagioToAttach = em.getReference(submissaoRelatorioEstagioListSubmissaoRelatorioEstagioToAttach.getClass(), submissaoRelatorioEstagioListSubmissaoRelatorioEstagioToAttach.getId());
                attachedSubmissaoRelatorioEstagioList.add(submissaoRelatorioEstagioListSubmissaoRelatorioEstagioToAttach);
            }
            pedido.setSubmissaoRelatorioEstagioList(attachedSubmissaoRelatorioEstagioList);
            List<SubmissaoMonografias> attachedSubmissaoMonografiasList = new ArrayList<SubmissaoMonografias>();
            for (SubmissaoMonografias submissaoMonografiasListSubmissaoMonografiasToAttach : pedido.getSubmissaoMonografiasList()) {
                submissaoMonografiasListSubmissaoMonografiasToAttach = em.getReference(submissaoMonografiasListSubmissaoMonografiasToAttach.getClass(), submissaoMonografiasListSubmissaoMonografiasToAttach.getId());
                attachedSubmissaoMonografiasList.add(submissaoMonografiasListSubmissaoMonografiasToAttach);
            }
            pedido.setSubmissaoMonografiasList(attachedSubmissaoMonografiasList);
            List<Declaracao> attachedDeclaracaoList = new ArrayList<Declaracao>();
            for (Declaracao declaracaoListDeclaracaoToAttach : pedido.getDeclaracaoList()) {
                declaracaoListDeclaracaoToAttach = em.getReference(declaracaoListDeclaracaoToAttach.getClass(), declaracaoListDeclaracaoToAttach.getId());
                attachedDeclaracaoList.add(declaracaoListDeclaracaoToAttach);
            }
            pedido.setDeclaracaoList(attachedDeclaracaoList);
            em.persist(pedido);
            if (estado != null) {
                estado.getPedidoList().add(pedido);
                estado = em.merge(estado);
            }
            if (tipoPedido != null) {
                tipoPedido.getPedidoList().add(pedido);
                tipoPedido = em.merge(tipoPedido);
            }
            if (utilizador != null) {
                utilizador.getPedidoList().add(pedido);
                utilizador = em.merge(utilizador);
            }
            for (Pedido pedidoListPedido : pedido.getPedidoList()) {
                pedidoListPedido.getPedidoList().add(pedido);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            for (Pedido pedidoList1Pedido : pedido.getPedidoList1()) {
                pedidoList1Pedido.getPedidoList().add(pedido);
                pedidoList1Pedido = em.merge(pedidoList1Pedido);
            }
            for (Pedido pedidoList2Pedido : pedido.getPedidoList2()) {
                pedidoList2Pedido.getPedidoList().add(pedido);
                pedidoList2Pedido = em.merge(pedidoList2Pedido);
            }
            for (Pedido pedidoList3Pedido : pedido.getPedidoList3()) {
                pedidoList3Pedido.getPedidoList2().add(pedido);
                pedidoList3Pedido = em.merge(pedidoList3Pedido);
            }
            for (Pedido pedidoList4Pedido : pedido.getPedidoList4()) {
                pedidoList4Pedido.getPedidoList().add(pedido);
                pedidoList4Pedido = em.merge(pedidoList4Pedido);
            }
            for (Pedido pedidoList5Pedido : pedido.getPedidoList5()) {
                pedidoList5Pedido.getPedidoList4().add(pedido);
                pedidoList5Pedido = em.merge(pedidoList5Pedido);
            }
            for (Pedido pedidoList6Pedido : pedido.getPedidoList6()) {
                pedidoList6Pedido.getPedidoList().add(pedido);
                pedidoList6Pedido = em.merge(pedidoList6Pedido);
            }
            for (Pedido pedidoList7Pedido : pedido.getPedidoList7()) {
                pedidoList7Pedido.getPedidoList6().add(pedido);
                pedidoList7Pedido = em.merge(pedidoList7Pedido);
            }
            for (RealizacaoExame realizacaoExameListRealizacaoExame : pedido.getRealizacaoExameList()) {
                Pedido oldPedidoOfRealizacaoExameListRealizacaoExame = realizacaoExameListRealizacaoExame.getPedido();
                realizacaoExameListRealizacaoExame.setPedido(pedido);
                realizacaoExameListRealizacaoExame = em.merge(realizacaoExameListRealizacaoExame);
                if (oldPedidoOfRealizacaoExameListRealizacaoExame != null) {
                    oldPedidoOfRealizacaoExameListRealizacaoExame.getRealizacaoExameList().remove(realizacaoExameListRealizacaoExame);
                    oldPedidoOfRealizacaoExameListRealizacaoExame = em.merge(oldPedidoOfRealizacaoExameListRealizacaoExame);
                }
            }
            for (MudancaCurso mudancaCursoListMudancaCurso : pedido.getMudancaCursoList()) {
                Pedido oldPedidoOfMudancaCursoListMudancaCurso = mudancaCursoListMudancaCurso.getPedido();
                mudancaCursoListMudancaCurso.setPedido(pedido);
                mudancaCursoListMudancaCurso = em.merge(mudancaCursoListMudancaCurso);
                if (oldPedidoOfMudancaCursoListMudancaCurso != null) {
                    oldPedidoOfMudancaCursoListMudancaCurso.getMudancaCursoList().remove(mudancaCursoListMudancaCurso);
                    oldPedidoOfMudancaCursoListMudancaCurso = em.merge(oldPedidoOfMudancaCursoListMudancaCurso);
                }
            }
            for (SituacaoDivida situacaoDividaListSituacaoDivida : pedido.getSituacaoDividaList()) {
                Pedido oldPedidoOfSituacaoDividaListSituacaoDivida = situacaoDividaListSituacaoDivida.getPedido();
                situacaoDividaListSituacaoDivida.setPedido(pedido);
                situacaoDividaListSituacaoDivida = em.merge(situacaoDividaListSituacaoDivida);
                if (oldPedidoOfSituacaoDividaListSituacaoDivida != null) {
                    oldPedidoOfSituacaoDividaListSituacaoDivida.getSituacaoDividaList().remove(situacaoDividaListSituacaoDivida);
                    oldPedidoOfSituacaoDividaListSituacaoDivida = em.merge(oldPedidoOfSituacaoDividaListSituacaoDivida);
                }
            }
            for (TratamentoPedido tratamentoPedidoListTratamentoPedido : pedido.getTratamentoPedidoList()) {
                Pedido oldPedidoOfTratamentoPedidoListTratamentoPedido = tratamentoPedidoListTratamentoPedido.getPedido();
                tratamentoPedidoListTratamentoPedido.setPedido(pedido);
                tratamentoPedidoListTratamentoPedido = em.merge(tratamentoPedidoListTratamentoPedido);
                if (oldPedidoOfTratamentoPedidoListTratamentoPedido != null) {
                    oldPedidoOfTratamentoPedidoListTratamentoPedido.getTratamentoPedidoList().remove(tratamentoPedidoListTratamentoPedido);
                    oldPedidoOfTratamentoPedidoListTratamentoPedido = em.merge(oldPedidoOfTratamentoPedidoListTratamentoPedido);
                }
            }
            for (MatriculaForaEpoca matriculaForaEpocaListMatriculaForaEpoca : pedido.getMatriculaForaEpocaList()) {
                Pedido oldPedidoOfMatriculaForaEpocaListMatriculaForaEpoca = matriculaForaEpocaListMatriculaForaEpoca.getPedido();
                matriculaForaEpocaListMatriculaForaEpoca.setPedido(pedido);
                matriculaForaEpocaListMatriculaForaEpoca = em.merge(matriculaForaEpocaListMatriculaForaEpoca);
                if (oldPedidoOfMatriculaForaEpocaListMatriculaForaEpoca != null) {
                    oldPedidoOfMatriculaForaEpocaListMatriculaForaEpoca.getMatriculaForaEpocaList().remove(matriculaForaEpocaListMatriculaForaEpoca);
                    oldPedidoOfMatriculaForaEpocaListMatriculaForaEpoca = em.merge(oldPedidoOfMatriculaForaEpocaListMatriculaForaEpoca);
                }
            }
            for (Movimento movimentoListMovimento : pedido.getMovimentoList()) {
                Pedido oldPedidoOfMovimentoListMovimento = movimentoListMovimento.getPedido();
                movimentoListMovimento.setPedido(pedido);
                movimentoListMovimento = em.merge(movimentoListMovimento);
                if (oldPedidoOfMovimentoListMovimento != null) {
                    oldPedidoOfMovimentoListMovimento.getMovimentoList().remove(movimentoListMovimento);
                    oldPedidoOfMovimentoListMovimento = em.merge(oldPedidoOfMovimentoListMovimento);
                }
            }
            for (PedidosCertificados pedidosCertificadosListPedidosCertificados : pedido.getPedidosCertificadosList()) {
                Pedido oldPedidoOfPedidosCertificadosListPedidosCertificados = pedidosCertificadosListPedidosCertificados.getPedido();
                pedidosCertificadosListPedidosCertificados.setPedido(pedido);
                pedidosCertificadosListPedidosCertificados = em.merge(pedidosCertificadosListPedidosCertificados);
                if (oldPedidoOfPedidosCertificadosListPedidosCertificados != null) {
                    oldPedidoOfPedidosCertificadosListPedidosCertificados.getPedidosCertificadosList().remove(pedidosCertificadosListPedidosCertificados);
                    oldPedidoOfPedidosCertificadosListPedidosCertificados = em.merge(oldPedidoOfPedidosCertificadosListPedidosCertificados);
                }
            }
            for (TransferenciaCrs transferenciaCrsListTransferenciaCrs : pedido.getTransferenciaCrsList()) {
                Pedido oldPedidoOfTransferenciaCrsListTransferenciaCrs = transferenciaCrsListTransferenciaCrs.getPedido();
                transferenciaCrsListTransferenciaCrs.setPedido(pedido);
                transferenciaCrsListTransferenciaCrs = em.merge(transferenciaCrsListTransferenciaCrs);
                if (oldPedidoOfTransferenciaCrsListTransferenciaCrs != null) {
                    oldPedidoOfTransferenciaCrsListTransferenciaCrs.getTransferenciaCrsList().remove(transferenciaCrsListTransferenciaCrs);
                    oldPedidoOfTransferenciaCrsListTransferenciaCrs = em.merge(oldPedidoOfTransferenciaCrsListTransferenciaCrs);
                }
            }
            for (ReintegracaoAcademica reintegracaoAcademicaListReintegracaoAcademica : pedido.getReintegracaoAcademicaList()) {
                Pedido oldPedidoOfReintegracaoAcademicaListReintegracaoAcademica = reintegracaoAcademicaListReintegracaoAcademica.getPedido();
                reintegracaoAcademicaListReintegracaoAcademica.setPedido(pedido);
                reintegracaoAcademicaListReintegracaoAcademica = em.merge(reintegracaoAcademicaListReintegracaoAcademica);
                if (oldPedidoOfReintegracaoAcademicaListReintegracaoAcademica != null) {
                    oldPedidoOfReintegracaoAcademicaListReintegracaoAcademica.getReintegracaoAcademicaList().remove(reintegracaoAcademicaListReintegracaoAcademica);
                    oldPedidoOfReintegracaoAcademicaListReintegracaoAcademica = em.merge(oldPedidoOfReintegracaoAcademicaListReintegracaoAcademica);
                }
            }
            for (Equivalencia equivalenciaListEquivalencia : pedido.getEquivalenciaList()) {
                Pedido oldPedidoOfEquivalenciaListEquivalencia = equivalenciaListEquivalencia.getPedido();
                equivalenciaListEquivalencia.setPedido(pedido);
                equivalenciaListEquivalencia = em.merge(equivalenciaListEquivalencia);
                if (oldPedidoOfEquivalenciaListEquivalencia != null) {
                    oldPedidoOfEquivalenciaListEquivalencia.getEquivalenciaList().remove(equivalenciaListEquivalencia);
                    oldPedidoOfEquivalenciaListEquivalencia = em.merge(oldPedidoOfEquivalenciaListEquivalencia);
                }
            }
            for (SubmissaoRelatorioEstagio submissaoRelatorioEstagioListSubmissaoRelatorioEstagio : pedido.getSubmissaoRelatorioEstagioList()) {
                Pedido oldPedidoOfSubmissaoRelatorioEstagioListSubmissaoRelatorioEstagio = submissaoRelatorioEstagioListSubmissaoRelatorioEstagio.getPedido();
                submissaoRelatorioEstagioListSubmissaoRelatorioEstagio.setPedido(pedido);
                submissaoRelatorioEstagioListSubmissaoRelatorioEstagio = em.merge(submissaoRelatorioEstagioListSubmissaoRelatorioEstagio);
                if (oldPedidoOfSubmissaoRelatorioEstagioListSubmissaoRelatorioEstagio != null) {
                    oldPedidoOfSubmissaoRelatorioEstagioListSubmissaoRelatorioEstagio.getSubmissaoRelatorioEstagioList().remove(submissaoRelatorioEstagioListSubmissaoRelatorioEstagio);
                    oldPedidoOfSubmissaoRelatorioEstagioListSubmissaoRelatorioEstagio = em.merge(oldPedidoOfSubmissaoRelatorioEstagioListSubmissaoRelatorioEstagio);
                }
            }
            for (SubmissaoMonografias submissaoMonografiasListSubmissaoMonografias : pedido.getSubmissaoMonografiasList()) {
                Pedido oldPedidoOfSubmissaoMonografiasListSubmissaoMonografias = submissaoMonografiasListSubmissaoMonografias.getPedido();
                submissaoMonografiasListSubmissaoMonografias.setPedido(pedido);
                submissaoMonografiasListSubmissaoMonografias = em.merge(submissaoMonografiasListSubmissaoMonografias);
                if (oldPedidoOfSubmissaoMonografiasListSubmissaoMonografias != null) {
                    oldPedidoOfSubmissaoMonografiasListSubmissaoMonografias.getSubmissaoMonografiasList().remove(submissaoMonografiasListSubmissaoMonografias);
                    oldPedidoOfSubmissaoMonografiasListSubmissaoMonografias = em.merge(oldPedidoOfSubmissaoMonografiasListSubmissaoMonografias);
                }
            }
            for (Declaracao declaracaoListDeclaracao : pedido.getDeclaracaoList()) {
                Pedido oldPedidoOfDeclaracaoListDeclaracao = declaracaoListDeclaracao.getPedido();
                declaracaoListDeclaracao.setPedido(pedido);
                declaracaoListDeclaracao = em.merge(declaracaoListDeclaracao);
                if (oldPedidoOfDeclaracaoListDeclaracao != null) {
                    oldPedidoOfDeclaracaoListDeclaracao.getDeclaracaoList().remove(declaracaoListDeclaracao);
                    oldPedidoOfDeclaracaoListDeclaracao = em.merge(oldPedidoOfDeclaracaoListDeclaracao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getId());
            Estado estadoOld = persistentPedido.getEstado();
            Estado estadoNew = pedido.getEstado();
            TipoPedido tipoPedidoOld = persistentPedido.getTipoPedido();
            TipoPedido tipoPedidoNew = pedido.getTipoPedido();
            Utilizador utilizadorOld = persistentPedido.getUtilizador();
            Utilizador utilizadorNew = pedido.getUtilizador();
            List<Pedido> pedidoListOld = persistentPedido.getPedidoList();
            List<Pedido> pedidoListNew = pedido.getPedidoList();
            List<Pedido> pedidoList1Old = persistentPedido.getPedidoList1();
            List<Pedido> pedidoList1New = pedido.getPedidoList1();
            List<Pedido> pedidoList2Old = persistentPedido.getPedidoList2();
            List<Pedido> pedidoList2New = pedido.getPedidoList2();
            List<Pedido> pedidoList3Old = persistentPedido.getPedidoList3();
            List<Pedido> pedidoList3New = pedido.getPedidoList3();
            List<Pedido> pedidoList4Old = persistentPedido.getPedidoList4();
            List<Pedido> pedidoList4New = pedido.getPedidoList4();
            List<Pedido> pedidoList5Old = persistentPedido.getPedidoList5();
            List<Pedido> pedidoList5New = pedido.getPedidoList5();
            List<Pedido> pedidoList6Old = persistentPedido.getPedidoList6();
            List<Pedido> pedidoList6New = pedido.getPedidoList6();
            List<Pedido> pedidoList7Old = persistentPedido.getPedidoList7();
            List<Pedido> pedidoList7New = pedido.getPedidoList7();
            List<RealizacaoExame> realizacaoExameListOld = persistentPedido.getRealizacaoExameList();
            List<RealizacaoExame> realizacaoExameListNew = pedido.getRealizacaoExameList();
            List<MudancaCurso> mudancaCursoListOld = persistentPedido.getMudancaCursoList();
            List<MudancaCurso> mudancaCursoListNew = pedido.getMudancaCursoList();
            List<SituacaoDivida> situacaoDividaListOld = persistentPedido.getSituacaoDividaList();
            List<SituacaoDivida> situacaoDividaListNew = pedido.getSituacaoDividaList();
            List<TratamentoPedido> tratamentoPedidoListOld = persistentPedido.getTratamentoPedidoList();
            List<TratamentoPedido> tratamentoPedidoListNew = pedido.getTratamentoPedidoList();
            List<MatriculaForaEpoca> matriculaForaEpocaListOld = persistentPedido.getMatriculaForaEpocaList();
            List<MatriculaForaEpoca> matriculaForaEpocaListNew = pedido.getMatriculaForaEpocaList();
            List<Movimento> movimentoListOld = persistentPedido.getMovimentoList();
            List<Movimento> movimentoListNew = pedido.getMovimentoList();
            List<PedidosCertificados> pedidosCertificadosListOld = persistentPedido.getPedidosCertificadosList();
            List<PedidosCertificados> pedidosCertificadosListNew = pedido.getPedidosCertificadosList();
            List<TransferenciaCrs> transferenciaCrsListOld = persistentPedido.getTransferenciaCrsList();
            List<TransferenciaCrs> transferenciaCrsListNew = pedido.getTransferenciaCrsList();
            List<ReintegracaoAcademica> reintegracaoAcademicaListOld = persistentPedido.getReintegracaoAcademicaList();
            List<ReintegracaoAcademica> reintegracaoAcademicaListNew = pedido.getReintegracaoAcademicaList();
            List<Equivalencia> equivalenciaListOld = persistentPedido.getEquivalenciaList();
            List<Equivalencia> equivalenciaListNew = pedido.getEquivalenciaList();
            List<SubmissaoRelatorioEstagio> submissaoRelatorioEstagioListOld = persistentPedido.getSubmissaoRelatorioEstagioList();
            List<SubmissaoRelatorioEstagio> submissaoRelatorioEstagioListNew = pedido.getSubmissaoRelatorioEstagioList();
            List<SubmissaoMonografias> submissaoMonografiasListOld = persistentPedido.getSubmissaoMonografiasList();
            List<SubmissaoMonografias> submissaoMonografiasListNew = pedido.getSubmissaoMonografiasList();
            List<Declaracao> declaracaoListOld = persistentPedido.getDeclaracaoList();
            List<Declaracao> declaracaoListNew = pedido.getDeclaracaoList();
            List<String> illegalOrphanMessages = null;
            for (RealizacaoExame realizacaoExameListOldRealizacaoExame : realizacaoExameListOld) {
                if (!realizacaoExameListNew.contains(realizacaoExameListOldRealizacaoExame)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RealizacaoExame " + realizacaoExameListOldRealizacaoExame + " since its pedido field is not nullable.");
                }
            }
            for (MudancaCurso mudancaCursoListOldMudancaCurso : mudancaCursoListOld) {
                if (!mudancaCursoListNew.contains(mudancaCursoListOldMudancaCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MudancaCurso " + mudancaCursoListOldMudancaCurso + " since its pedido field is not nullable.");
                }
            }
            for (SituacaoDivida situacaoDividaListOldSituacaoDivida : situacaoDividaListOld) {
                if (!situacaoDividaListNew.contains(situacaoDividaListOldSituacaoDivida)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SituacaoDivida " + situacaoDividaListOldSituacaoDivida + " since its pedido field is not nullable.");
                }
            }
            for (TratamentoPedido tratamentoPedidoListOldTratamentoPedido : tratamentoPedidoListOld) {
                if (!tratamentoPedidoListNew.contains(tratamentoPedidoListOldTratamentoPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TratamentoPedido " + tratamentoPedidoListOldTratamentoPedido + " since its pedido field is not nullable.");
                }
            }
            for (MatriculaForaEpoca matriculaForaEpocaListOldMatriculaForaEpoca : matriculaForaEpocaListOld) {
                if (!matriculaForaEpocaListNew.contains(matriculaForaEpocaListOldMatriculaForaEpoca)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MatriculaForaEpoca " + matriculaForaEpocaListOldMatriculaForaEpoca + " since its pedido field is not nullable.");
                }
            }
            for (Movimento movimentoListOldMovimento : movimentoListOld) {
                if (!movimentoListNew.contains(movimentoListOldMovimento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Movimento " + movimentoListOldMovimento + " since its pedido field is not nullable.");
                }
            }
            for (PedidosCertificados pedidosCertificadosListOldPedidosCertificados : pedidosCertificadosListOld) {
                if (!pedidosCertificadosListNew.contains(pedidosCertificadosListOldPedidosCertificados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidosCertificados " + pedidosCertificadosListOldPedidosCertificados + " since its pedido field is not nullable.");
                }
            }
            for (TransferenciaCrs transferenciaCrsListOldTransferenciaCrs : transferenciaCrsListOld) {
                if (!transferenciaCrsListNew.contains(transferenciaCrsListOldTransferenciaCrs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransferenciaCrs " + transferenciaCrsListOldTransferenciaCrs + " since its pedido field is not nullable.");
                }
            }
            for (ReintegracaoAcademica reintegracaoAcademicaListOldReintegracaoAcademica : reintegracaoAcademicaListOld) {
                if (!reintegracaoAcademicaListNew.contains(reintegracaoAcademicaListOldReintegracaoAcademica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReintegracaoAcademica " + reintegracaoAcademicaListOldReintegracaoAcademica + " since its pedido field is not nullable.");
                }
            }
            for (Equivalencia equivalenciaListOldEquivalencia : equivalenciaListOld) {
                if (!equivalenciaListNew.contains(equivalenciaListOldEquivalencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equivalencia " + equivalenciaListOldEquivalencia + " since its pedido field is not nullable.");
                }
            }
            for (SubmissaoRelatorioEstagio submissaoRelatorioEstagioListOldSubmissaoRelatorioEstagio : submissaoRelatorioEstagioListOld) {
                if (!submissaoRelatorioEstagioListNew.contains(submissaoRelatorioEstagioListOldSubmissaoRelatorioEstagio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SubmissaoRelatorioEstagio " + submissaoRelatorioEstagioListOldSubmissaoRelatorioEstagio + " since its pedido field is not nullable.");
                }
            }
            for (SubmissaoMonografias submissaoMonografiasListOldSubmissaoMonografias : submissaoMonografiasListOld) {
                if (!submissaoMonografiasListNew.contains(submissaoMonografiasListOldSubmissaoMonografias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SubmissaoMonografias " + submissaoMonografiasListOldSubmissaoMonografias + " since its pedido field is not nullable.");
                }
            }
            for (Declaracao declaracaoListOldDeclaracao : declaracaoListOld) {
                if (!declaracaoListNew.contains(declaracaoListOldDeclaracao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Declaracao " + declaracaoListOldDeclaracao + " since its pedido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getId());
                pedido.setEstado(estadoNew);
            }
            if (tipoPedidoNew != null) {
                tipoPedidoNew = em.getReference(tipoPedidoNew.getClass(), tipoPedidoNew.getId());
                pedido.setTipoPedido(tipoPedidoNew);
            }
            if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getId());
                pedido.setUtilizador(utilizadorNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getId());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            pedido.setPedidoList(pedidoListNew);
            List<Pedido> attachedPedidoList1New = new ArrayList<Pedido>();
            for (Pedido pedidoList1NewPedidoToAttach : pedidoList1New) {
                pedidoList1NewPedidoToAttach = em.getReference(pedidoList1NewPedidoToAttach.getClass(), pedidoList1NewPedidoToAttach.getId());
                attachedPedidoList1New.add(pedidoList1NewPedidoToAttach);
            }
            pedidoList1New = attachedPedidoList1New;
            pedido.setPedidoList1(pedidoList1New);
            List<Pedido> attachedPedidoList2New = new ArrayList<Pedido>();
            for (Pedido pedidoList2NewPedidoToAttach : pedidoList2New) {
                pedidoList2NewPedidoToAttach = em.getReference(pedidoList2NewPedidoToAttach.getClass(), pedidoList2NewPedidoToAttach.getId());
                attachedPedidoList2New.add(pedidoList2NewPedidoToAttach);
            }
            pedidoList2New = attachedPedidoList2New;
            pedido.setPedidoList2(pedidoList2New);
            List<Pedido> attachedPedidoList3New = new ArrayList<Pedido>();
            for (Pedido pedidoList3NewPedidoToAttach : pedidoList3New) {
                pedidoList3NewPedidoToAttach = em.getReference(pedidoList3NewPedidoToAttach.getClass(), pedidoList3NewPedidoToAttach.getId());
                attachedPedidoList3New.add(pedidoList3NewPedidoToAttach);
            }
            pedidoList3New = attachedPedidoList3New;
            pedido.setPedidoList3(pedidoList3New);
            List<Pedido> attachedPedidoList4New = new ArrayList<Pedido>();
            for (Pedido pedidoList4NewPedidoToAttach : pedidoList4New) {
                pedidoList4NewPedidoToAttach = em.getReference(pedidoList4NewPedidoToAttach.getClass(), pedidoList4NewPedidoToAttach.getId());
                attachedPedidoList4New.add(pedidoList4NewPedidoToAttach);
            }
            pedidoList4New = attachedPedidoList4New;
            pedido.setPedidoList4(pedidoList4New);
            List<Pedido> attachedPedidoList5New = new ArrayList<Pedido>();
            for (Pedido pedidoList5NewPedidoToAttach : pedidoList5New) {
                pedidoList5NewPedidoToAttach = em.getReference(pedidoList5NewPedidoToAttach.getClass(), pedidoList5NewPedidoToAttach.getId());
                attachedPedidoList5New.add(pedidoList5NewPedidoToAttach);
            }
            pedidoList5New = attachedPedidoList5New;
            pedido.setPedidoList5(pedidoList5New);
            List<Pedido> attachedPedidoList6New = new ArrayList<Pedido>();
            for (Pedido pedidoList6NewPedidoToAttach : pedidoList6New) {
                pedidoList6NewPedidoToAttach = em.getReference(pedidoList6NewPedidoToAttach.getClass(), pedidoList6NewPedidoToAttach.getId());
                attachedPedidoList6New.add(pedidoList6NewPedidoToAttach);
            }
            pedidoList6New = attachedPedidoList6New;
            pedido.setPedidoList6(pedidoList6New);
            List<Pedido> attachedPedidoList7New = new ArrayList<Pedido>();
            for (Pedido pedidoList7NewPedidoToAttach : pedidoList7New) {
                pedidoList7NewPedidoToAttach = em.getReference(pedidoList7NewPedidoToAttach.getClass(), pedidoList7NewPedidoToAttach.getId());
                attachedPedidoList7New.add(pedidoList7NewPedidoToAttach);
            }
            pedidoList7New = attachedPedidoList7New;
            pedido.setPedidoList7(pedidoList7New);
            List<RealizacaoExame> attachedRealizacaoExameListNew = new ArrayList<RealizacaoExame>();
            for (RealizacaoExame realizacaoExameListNewRealizacaoExameToAttach : realizacaoExameListNew) {
                realizacaoExameListNewRealizacaoExameToAttach = em.getReference(realizacaoExameListNewRealizacaoExameToAttach.getClass(), realizacaoExameListNewRealizacaoExameToAttach.getId());
                attachedRealizacaoExameListNew.add(realizacaoExameListNewRealizacaoExameToAttach);
            }
            realizacaoExameListNew = attachedRealizacaoExameListNew;
            pedido.setRealizacaoExameList(realizacaoExameListNew);
            List<MudancaCurso> attachedMudancaCursoListNew = new ArrayList<MudancaCurso>();
            for (MudancaCurso mudancaCursoListNewMudancaCursoToAttach : mudancaCursoListNew) {
                mudancaCursoListNewMudancaCursoToAttach = em.getReference(mudancaCursoListNewMudancaCursoToAttach.getClass(), mudancaCursoListNewMudancaCursoToAttach.getId());
                attachedMudancaCursoListNew.add(mudancaCursoListNewMudancaCursoToAttach);
            }
            mudancaCursoListNew = attachedMudancaCursoListNew;
            pedido.setMudancaCursoList(mudancaCursoListNew);
            List<SituacaoDivida> attachedSituacaoDividaListNew = new ArrayList<SituacaoDivida>();
            for (SituacaoDivida situacaoDividaListNewSituacaoDividaToAttach : situacaoDividaListNew) {
                situacaoDividaListNewSituacaoDividaToAttach = em.getReference(situacaoDividaListNewSituacaoDividaToAttach.getClass(), situacaoDividaListNewSituacaoDividaToAttach.getId());
                attachedSituacaoDividaListNew.add(situacaoDividaListNewSituacaoDividaToAttach);
            }
            situacaoDividaListNew = attachedSituacaoDividaListNew;
            pedido.setSituacaoDividaList(situacaoDividaListNew);
            List<TratamentoPedido> attachedTratamentoPedidoListNew = new ArrayList<TratamentoPedido>();
            for (TratamentoPedido tratamentoPedidoListNewTratamentoPedidoToAttach : tratamentoPedidoListNew) {
                tratamentoPedidoListNewTratamentoPedidoToAttach = em.getReference(tratamentoPedidoListNewTratamentoPedidoToAttach.getClass(), tratamentoPedidoListNewTratamentoPedidoToAttach.getId());
                attachedTratamentoPedidoListNew.add(tratamentoPedidoListNewTratamentoPedidoToAttach);
            }
            tratamentoPedidoListNew = attachedTratamentoPedidoListNew;
            pedido.setTratamentoPedidoList(tratamentoPedidoListNew);
            List<MatriculaForaEpoca> attachedMatriculaForaEpocaListNew = new ArrayList<MatriculaForaEpoca>();
            for (MatriculaForaEpoca matriculaForaEpocaListNewMatriculaForaEpocaToAttach : matriculaForaEpocaListNew) {
                matriculaForaEpocaListNewMatriculaForaEpocaToAttach = em.getReference(matriculaForaEpocaListNewMatriculaForaEpocaToAttach.getClass(), matriculaForaEpocaListNewMatriculaForaEpocaToAttach.getId());
                attachedMatriculaForaEpocaListNew.add(matriculaForaEpocaListNewMatriculaForaEpocaToAttach);
            }
            matriculaForaEpocaListNew = attachedMatriculaForaEpocaListNew;
            pedido.setMatriculaForaEpocaList(matriculaForaEpocaListNew);
            List<Movimento> attachedMovimentoListNew = new ArrayList<Movimento>();
            for (Movimento movimentoListNewMovimentoToAttach : movimentoListNew) {
                movimentoListNewMovimentoToAttach = em.getReference(movimentoListNewMovimentoToAttach.getClass(), movimentoListNewMovimentoToAttach.getId());
                attachedMovimentoListNew.add(movimentoListNewMovimentoToAttach);
            }
            movimentoListNew = attachedMovimentoListNew;
            pedido.setMovimentoList(movimentoListNew);
            List<PedidosCertificados> attachedPedidosCertificadosListNew = new ArrayList<PedidosCertificados>();
            for (PedidosCertificados pedidosCertificadosListNewPedidosCertificadosToAttach : pedidosCertificadosListNew) {
                pedidosCertificadosListNewPedidosCertificadosToAttach = em.getReference(pedidosCertificadosListNewPedidosCertificadosToAttach.getClass(), pedidosCertificadosListNewPedidosCertificadosToAttach.getId());
                attachedPedidosCertificadosListNew.add(pedidosCertificadosListNewPedidosCertificadosToAttach);
            }
            pedidosCertificadosListNew = attachedPedidosCertificadosListNew;
            pedido.setPedidosCertificadosList(pedidosCertificadosListNew);
            List<TransferenciaCrs> attachedTransferenciaCrsListNew = new ArrayList<TransferenciaCrs>();
            for (TransferenciaCrs transferenciaCrsListNewTransferenciaCrsToAttach : transferenciaCrsListNew) {
                transferenciaCrsListNewTransferenciaCrsToAttach = em.getReference(transferenciaCrsListNewTransferenciaCrsToAttach.getClass(), transferenciaCrsListNewTransferenciaCrsToAttach.getId());
                attachedTransferenciaCrsListNew.add(transferenciaCrsListNewTransferenciaCrsToAttach);
            }
            transferenciaCrsListNew = attachedTransferenciaCrsListNew;
            pedido.setTransferenciaCrsList(transferenciaCrsListNew);
            List<ReintegracaoAcademica> attachedReintegracaoAcademicaListNew = new ArrayList<ReintegracaoAcademica>();
            for (ReintegracaoAcademica reintegracaoAcademicaListNewReintegracaoAcademicaToAttach : reintegracaoAcademicaListNew) {
                reintegracaoAcademicaListNewReintegracaoAcademicaToAttach = em.getReference(reintegracaoAcademicaListNewReintegracaoAcademicaToAttach.getClass(), reintegracaoAcademicaListNewReintegracaoAcademicaToAttach.getId());
                attachedReintegracaoAcademicaListNew.add(reintegracaoAcademicaListNewReintegracaoAcademicaToAttach);
            }
            reintegracaoAcademicaListNew = attachedReintegracaoAcademicaListNew;
            pedido.setReintegracaoAcademicaList(reintegracaoAcademicaListNew);
            List<Equivalencia> attachedEquivalenciaListNew = new ArrayList<Equivalencia>();
            for (Equivalencia equivalenciaListNewEquivalenciaToAttach : equivalenciaListNew) {
                equivalenciaListNewEquivalenciaToAttach = em.getReference(equivalenciaListNewEquivalenciaToAttach.getClass(), equivalenciaListNewEquivalenciaToAttach.getId());
                attachedEquivalenciaListNew.add(equivalenciaListNewEquivalenciaToAttach);
            }
            equivalenciaListNew = attachedEquivalenciaListNew;
            pedido.setEquivalenciaList(equivalenciaListNew);
            List<SubmissaoRelatorioEstagio> attachedSubmissaoRelatorioEstagioListNew = new ArrayList<SubmissaoRelatorioEstagio>();
            for (SubmissaoRelatorioEstagio submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagioToAttach : submissaoRelatorioEstagioListNew) {
                submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagioToAttach = em.getReference(submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagioToAttach.getClass(), submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagioToAttach.getId());
                attachedSubmissaoRelatorioEstagioListNew.add(submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagioToAttach);
            }
            submissaoRelatorioEstagioListNew = attachedSubmissaoRelatorioEstagioListNew;
            pedido.setSubmissaoRelatorioEstagioList(submissaoRelatorioEstagioListNew);
            List<SubmissaoMonografias> attachedSubmissaoMonografiasListNew = new ArrayList<SubmissaoMonografias>();
            for (SubmissaoMonografias submissaoMonografiasListNewSubmissaoMonografiasToAttach : submissaoMonografiasListNew) {
                submissaoMonografiasListNewSubmissaoMonografiasToAttach = em.getReference(submissaoMonografiasListNewSubmissaoMonografiasToAttach.getClass(), submissaoMonografiasListNewSubmissaoMonografiasToAttach.getId());
                attachedSubmissaoMonografiasListNew.add(submissaoMonografiasListNewSubmissaoMonografiasToAttach);
            }
            submissaoMonografiasListNew = attachedSubmissaoMonografiasListNew;
            pedido.setSubmissaoMonografiasList(submissaoMonografiasListNew);
            List<Declaracao> attachedDeclaracaoListNew = new ArrayList<Declaracao>();
            for (Declaracao declaracaoListNewDeclaracaoToAttach : declaracaoListNew) {
                declaracaoListNewDeclaracaoToAttach = em.getReference(declaracaoListNewDeclaracaoToAttach.getClass(), declaracaoListNewDeclaracaoToAttach.getId());
                attachedDeclaracaoListNew.add(declaracaoListNewDeclaracaoToAttach);
            }
            declaracaoListNew = attachedDeclaracaoListNew;
            pedido.setDeclaracaoList(declaracaoListNew);
            pedido = em.merge(pedido);
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getPedidoList().remove(pedido);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getPedidoList().add(pedido);
                estadoNew = em.merge(estadoNew);
            }
            if (tipoPedidoOld != null && !tipoPedidoOld.equals(tipoPedidoNew)) {
                tipoPedidoOld.getPedidoList().remove(pedido);
                tipoPedidoOld = em.merge(tipoPedidoOld);
            }
            if (tipoPedidoNew != null && !tipoPedidoNew.equals(tipoPedidoOld)) {
                tipoPedidoNew.getPedidoList().add(pedido);
                tipoPedidoNew = em.merge(tipoPedidoNew);
            }
            if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getPedidoList().remove(pedido);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getPedidoList().add(pedido);
                utilizadorNew = em.merge(utilizadorNew);
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.getPedidoList().remove(pedido);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    pedidoListNewPedido.getPedidoList().add(pedido);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                }
            }
            for (Pedido pedidoList1OldPedido : pedidoList1Old) {
                if (!pedidoList1New.contains(pedidoList1OldPedido)) {
                    pedidoList1OldPedido.getPedidoList().remove(pedido);
                    pedidoList1OldPedido = em.merge(pedidoList1OldPedido);
                }
            }
            for (Pedido pedidoList1NewPedido : pedidoList1New) {
                if (!pedidoList1Old.contains(pedidoList1NewPedido)) {
                    pedidoList1NewPedido.getPedidoList().add(pedido);
                    pedidoList1NewPedido = em.merge(pedidoList1NewPedido);
                }
            }
            for (Pedido pedidoList2OldPedido : pedidoList2Old) {
                if (!pedidoList2New.contains(pedidoList2OldPedido)) {
                    pedidoList2OldPedido.getPedidoList().remove(pedido);
                    pedidoList2OldPedido = em.merge(pedidoList2OldPedido);
                }
            }
            for (Pedido pedidoList2NewPedido : pedidoList2New) {
                if (!pedidoList2Old.contains(pedidoList2NewPedido)) {
                    pedidoList2NewPedido.getPedidoList().add(pedido);
                    pedidoList2NewPedido = em.merge(pedidoList2NewPedido);
                }
            }
            for (Pedido pedidoList3OldPedido : pedidoList3Old) {
                if (!pedidoList3New.contains(pedidoList3OldPedido)) {
                    pedidoList3OldPedido.getPedidoList2().remove(pedido);
                    pedidoList3OldPedido = em.merge(pedidoList3OldPedido);
                }
            }
            for (Pedido pedidoList3NewPedido : pedidoList3New) {
                if (!pedidoList3Old.contains(pedidoList3NewPedido)) {
                    pedidoList3NewPedido.getPedidoList2().add(pedido);
                    pedidoList3NewPedido = em.merge(pedidoList3NewPedido);
                }
            }
            for (Pedido pedidoList4OldPedido : pedidoList4Old) {
                if (!pedidoList4New.contains(pedidoList4OldPedido)) {
                    pedidoList4OldPedido.getPedidoList().remove(pedido);
                    pedidoList4OldPedido = em.merge(pedidoList4OldPedido);
                }
            }
            for (Pedido pedidoList4NewPedido : pedidoList4New) {
                if (!pedidoList4Old.contains(pedidoList4NewPedido)) {
                    pedidoList4NewPedido.getPedidoList().add(pedido);
                    pedidoList4NewPedido = em.merge(pedidoList4NewPedido);
                }
            }
            for (Pedido pedidoList5OldPedido : pedidoList5Old) {
                if (!pedidoList5New.contains(pedidoList5OldPedido)) {
                    pedidoList5OldPedido.getPedidoList4().remove(pedido);
                    pedidoList5OldPedido = em.merge(pedidoList5OldPedido);
                }
            }
            for (Pedido pedidoList5NewPedido : pedidoList5New) {
                if (!pedidoList5Old.contains(pedidoList5NewPedido)) {
                    pedidoList5NewPedido.getPedidoList4().add(pedido);
                    pedidoList5NewPedido = em.merge(pedidoList5NewPedido);
                }
            }
            for (Pedido pedidoList6OldPedido : pedidoList6Old) {
                if (!pedidoList6New.contains(pedidoList6OldPedido)) {
                    pedidoList6OldPedido.getPedidoList().remove(pedido);
                    pedidoList6OldPedido = em.merge(pedidoList6OldPedido);
                }
            }
            for (Pedido pedidoList6NewPedido : pedidoList6New) {
                if (!pedidoList6Old.contains(pedidoList6NewPedido)) {
                    pedidoList6NewPedido.getPedidoList().add(pedido);
                    pedidoList6NewPedido = em.merge(pedidoList6NewPedido);
                }
            }
            for (Pedido pedidoList7OldPedido : pedidoList7Old) {
                if (!pedidoList7New.contains(pedidoList7OldPedido)) {
                    pedidoList7OldPedido.getPedidoList6().remove(pedido);
                    pedidoList7OldPedido = em.merge(pedidoList7OldPedido);
                }
            }
            for (Pedido pedidoList7NewPedido : pedidoList7New) {
                if (!pedidoList7Old.contains(pedidoList7NewPedido)) {
                    pedidoList7NewPedido.getPedidoList6().add(pedido);
                    pedidoList7NewPedido = em.merge(pedidoList7NewPedido);
                }
            }
            for (RealizacaoExame realizacaoExameListNewRealizacaoExame : realizacaoExameListNew) {
                if (!realizacaoExameListOld.contains(realizacaoExameListNewRealizacaoExame)) {
                    Pedido oldPedidoOfRealizacaoExameListNewRealizacaoExame = realizacaoExameListNewRealizacaoExame.getPedido();
                    realizacaoExameListNewRealizacaoExame.setPedido(pedido);
                    realizacaoExameListNewRealizacaoExame = em.merge(realizacaoExameListNewRealizacaoExame);
                    if (oldPedidoOfRealizacaoExameListNewRealizacaoExame != null && !oldPedidoOfRealizacaoExameListNewRealizacaoExame.equals(pedido)) {
                        oldPedidoOfRealizacaoExameListNewRealizacaoExame.getRealizacaoExameList().remove(realizacaoExameListNewRealizacaoExame);
                        oldPedidoOfRealizacaoExameListNewRealizacaoExame = em.merge(oldPedidoOfRealizacaoExameListNewRealizacaoExame);
                    }
                }
            }
            for (MudancaCurso mudancaCursoListNewMudancaCurso : mudancaCursoListNew) {
                if (!mudancaCursoListOld.contains(mudancaCursoListNewMudancaCurso)) {
                    Pedido oldPedidoOfMudancaCursoListNewMudancaCurso = mudancaCursoListNewMudancaCurso.getPedido();
                    mudancaCursoListNewMudancaCurso.setPedido(pedido);
                    mudancaCursoListNewMudancaCurso = em.merge(mudancaCursoListNewMudancaCurso);
                    if (oldPedidoOfMudancaCursoListNewMudancaCurso != null && !oldPedidoOfMudancaCursoListNewMudancaCurso.equals(pedido)) {
                        oldPedidoOfMudancaCursoListNewMudancaCurso.getMudancaCursoList().remove(mudancaCursoListNewMudancaCurso);
                        oldPedidoOfMudancaCursoListNewMudancaCurso = em.merge(oldPedidoOfMudancaCursoListNewMudancaCurso);
                    }
                }
            }
            for (SituacaoDivida situacaoDividaListNewSituacaoDivida : situacaoDividaListNew) {
                if (!situacaoDividaListOld.contains(situacaoDividaListNewSituacaoDivida)) {
                    Pedido oldPedidoOfSituacaoDividaListNewSituacaoDivida = situacaoDividaListNewSituacaoDivida.getPedido();
                    situacaoDividaListNewSituacaoDivida.setPedido(pedido);
                    situacaoDividaListNewSituacaoDivida = em.merge(situacaoDividaListNewSituacaoDivida);
                    if (oldPedidoOfSituacaoDividaListNewSituacaoDivida != null && !oldPedidoOfSituacaoDividaListNewSituacaoDivida.equals(pedido)) {
                        oldPedidoOfSituacaoDividaListNewSituacaoDivida.getSituacaoDividaList().remove(situacaoDividaListNewSituacaoDivida);
                        oldPedidoOfSituacaoDividaListNewSituacaoDivida = em.merge(oldPedidoOfSituacaoDividaListNewSituacaoDivida);
                    }
                }
            }
            for (TratamentoPedido tratamentoPedidoListNewTratamentoPedido : tratamentoPedidoListNew) {
                if (!tratamentoPedidoListOld.contains(tratamentoPedidoListNewTratamentoPedido)) {
                    Pedido oldPedidoOfTratamentoPedidoListNewTratamentoPedido = tratamentoPedidoListNewTratamentoPedido.getPedido();
                    tratamentoPedidoListNewTratamentoPedido.setPedido(pedido);
                    tratamentoPedidoListNewTratamentoPedido = em.merge(tratamentoPedidoListNewTratamentoPedido);
                    if (oldPedidoOfTratamentoPedidoListNewTratamentoPedido != null && !oldPedidoOfTratamentoPedidoListNewTratamentoPedido.equals(pedido)) {
                        oldPedidoOfTratamentoPedidoListNewTratamentoPedido.getTratamentoPedidoList().remove(tratamentoPedidoListNewTratamentoPedido);
                        oldPedidoOfTratamentoPedidoListNewTratamentoPedido = em.merge(oldPedidoOfTratamentoPedidoListNewTratamentoPedido);
                    }
                }
            }
            for (MatriculaForaEpoca matriculaForaEpocaListNewMatriculaForaEpoca : matriculaForaEpocaListNew) {
                if (!matriculaForaEpocaListOld.contains(matriculaForaEpocaListNewMatriculaForaEpoca)) {
                    Pedido oldPedidoOfMatriculaForaEpocaListNewMatriculaForaEpoca = matriculaForaEpocaListNewMatriculaForaEpoca.getPedido();
                    matriculaForaEpocaListNewMatriculaForaEpoca.setPedido(pedido);
                    matriculaForaEpocaListNewMatriculaForaEpoca = em.merge(matriculaForaEpocaListNewMatriculaForaEpoca);
                    if (oldPedidoOfMatriculaForaEpocaListNewMatriculaForaEpoca != null && !oldPedidoOfMatriculaForaEpocaListNewMatriculaForaEpoca.equals(pedido)) {
                        oldPedidoOfMatriculaForaEpocaListNewMatriculaForaEpoca.getMatriculaForaEpocaList().remove(matriculaForaEpocaListNewMatriculaForaEpoca);
                        oldPedidoOfMatriculaForaEpocaListNewMatriculaForaEpoca = em.merge(oldPedidoOfMatriculaForaEpocaListNewMatriculaForaEpoca);
                    }
                }
            }
            for (Movimento movimentoListNewMovimento : movimentoListNew) {
                if (!movimentoListOld.contains(movimentoListNewMovimento)) {
                    Pedido oldPedidoOfMovimentoListNewMovimento = movimentoListNewMovimento.getPedido();
                    movimentoListNewMovimento.setPedido(pedido);
                    movimentoListNewMovimento = em.merge(movimentoListNewMovimento);
                    if (oldPedidoOfMovimentoListNewMovimento != null && !oldPedidoOfMovimentoListNewMovimento.equals(pedido)) {
                        oldPedidoOfMovimentoListNewMovimento.getMovimentoList().remove(movimentoListNewMovimento);
                        oldPedidoOfMovimentoListNewMovimento = em.merge(oldPedidoOfMovimentoListNewMovimento);
                    }
                }
            }
            for (PedidosCertificados pedidosCertificadosListNewPedidosCertificados : pedidosCertificadosListNew) {
                if (!pedidosCertificadosListOld.contains(pedidosCertificadosListNewPedidosCertificados)) {
                    Pedido oldPedidoOfPedidosCertificadosListNewPedidosCertificados = pedidosCertificadosListNewPedidosCertificados.getPedido();
                    pedidosCertificadosListNewPedidosCertificados.setPedido(pedido);
                    pedidosCertificadosListNewPedidosCertificados = em.merge(pedidosCertificadosListNewPedidosCertificados);
                    if (oldPedidoOfPedidosCertificadosListNewPedidosCertificados != null && !oldPedidoOfPedidosCertificadosListNewPedidosCertificados.equals(pedido)) {
                        oldPedidoOfPedidosCertificadosListNewPedidosCertificados.getPedidosCertificadosList().remove(pedidosCertificadosListNewPedidosCertificados);
                        oldPedidoOfPedidosCertificadosListNewPedidosCertificados = em.merge(oldPedidoOfPedidosCertificadosListNewPedidosCertificados);
                    }
                }
            }
            for (TransferenciaCrs transferenciaCrsListNewTransferenciaCrs : transferenciaCrsListNew) {
                if (!transferenciaCrsListOld.contains(transferenciaCrsListNewTransferenciaCrs)) {
                    Pedido oldPedidoOfTransferenciaCrsListNewTransferenciaCrs = transferenciaCrsListNewTransferenciaCrs.getPedido();
                    transferenciaCrsListNewTransferenciaCrs.setPedido(pedido);
                    transferenciaCrsListNewTransferenciaCrs = em.merge(transferenciaCrsListNewTransferenciaCrs);
                    if (oldPedidoOfTransferenciaCrsListNewTransferenciaCrs != null && !oldPedidoOfTransferenciaCrsListNewTransferenciaCrs.equals(pedido)) {
                        oldPedidoOfTransferenciaCrsListNewTransferenciaCrs.getTransferenciaCrsList().remove(transferenciaCrsListNewTransferenciaCrs);
                        oldPedidoOfTransferenciaCrsListNewTransferenciaCrs = em.merge(oldPedidoOfTransferenciaCrsListNewTransferenciaCrs);
                    }
                }
            }
            for (ReintegracaoAcademica reintegracaoAcademicaListNewReintegracaoAcademica : reintegracaoAcademicaListNew) {
                if (!reintegracaoAcademicaListOld.contains(reintegracaoAcademicaListNewReintegracaoAcademica)) {
                    Pedido oldPedidoOfReintegracaoAcademicaListNewReintegracaoAcademica = reintegracaoAcademicaListNewReintegracaoAcademica.getPedido();
                    reintegracaoAcademicaListNewReintegracaoAcademica.setPedido(pedido);
                    reintegracaoAcademicaListNewReintegracaoAcademica = em.merge(reintegracaoAcademicaListNewReintegracaoAcademica);
                    if (oldPedidoOfReintegracaoAcademicaListNewReintegracaoAcademica != null && !oldPedidoOfReintegracaoAcademicaListNewReintegracaoAcademica.equals(pedido)) {
                        oldPedidoOfReintegracaoAcademicaListNewReintegracaoAcademica.getReintegracaoAcademicaList().remove(reintegracaoAcademicaListNewReintegracaoAcademica);
                        oldPedidoOfReintegracaoAcademicaListNewReintegracaoAcademica = em.merge(oldPedidoOfReintegracaoAcademicaListNewReintegracaoAcademica);
                    }
                }
            }
            for (Equivalencia equivalenciaListNewEquivalencia : equivalenciaListNew) {
                if (!equivalenciaListOld.contains(equivalenciaListNewEquivalencia)) {
                    Pedido oldPedidoOfEquivalenciaListNewEquivalencia = equivalenciaListNewEquivalencia.getPedido();
                    equivalenciaListNewEquivalencia.setPedido(pedido);
                    equivalenciaListNewEquivalencia = em.merge(equivalenciaListNewEquivalencia);
                    if (oldPedidoOfEquivalenciaListNewEquivalencia != null && !oldPedidoOfEquivalenciaListNewEquivalencia.equals(pedido)) {
                        oldPedidoOfEquivalenciaListNewEquivalencia.getEquivalenciaList().remove(equivalenciaListNewEquivalencia);
                        oldPedidoOfEquivalenciaListNewEquivalencia = em.merge(oldPedidoOfEquivalenciaListNewEquivalencia);
                    }
                }
            }
            for (SubmissaoRelatorioEstagio submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio : submissaoRelatorioEstagioListNew) {
                if (!submissaoRelatorioEstagioListOld.contains(submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio)) {
                    Pedido oldPedidoOfSubmissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio = submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio.getPedido();
                    submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio.setPedido(pedido);
                    submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio = em.merge(submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio);
                    if (oldPedidoOfSubmissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio != null && !oldPedidoOfSubmissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio.equals(pedido)) {
                        oldPedidoOfSubmissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio.getSubmissaoRelatorioEstagioList().remove(submissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio);
                        oldPedidoOfSubmissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio = em.merge(oldPedidoOfSubmissaoRelatorioEstagioListNewSubmissaoRelatorioEstagio);
                    }
                }
            }
            for (SubmissaoMonografias submissaoMonografiasListNewSubmissaoMonografias : submissaoMonografiasListNew) {
                if (!submissaoMonografiasListOld.contains(submissaoMonografiasListNewSubmissaoMonografias)) {
                    Pedido oldPedidoOfSubmissaoMonografiasListNewSubmissaoMonografias = submissaoMonografiasListNewSubmissaoMonografias.getPedido();
                    submissaoMonografiasListNewSubmissaoMonografias.setPedido(pedido);
                    submissaoMonografiasListNewSubmissaoMonografias = em.merge(submissaoMonografiasListNewSubmissaoMonografias);
                    if (oldPedidoOfSubmissaoMonografiasListNewSubmissaoMonografias != null && !oldPedidoOfSubmissaoMonografiasListNewSubmissaoMonografias.equals(pedido)) {
                        oldPedidoOfSubmissaoMonografiasListNewSubmissaoMonografias.getSubmissaoMonografiasList().remove(submissaoMonografiasListNewSubmissaoMonografias);
                        oldPedidoOfSubmissaoMonografiasListNewSubmissaoMonografias = em.merge(oldPedidoOfSubmissaoMonografiasListNewSubmissaoMonografias);
                    }
                }
            }
            for (Declaracao declaracaoListNewDeclaracao : declaracaoListNew) {
                if (!declaracaoListOld.contains(declaracaoListNewDeclaracao)) {
                    Pedido oldPedidoOfDeclaracaoListNewDeclaracao = declaracaoListNewDeclaracao.getPedido();
                    declaracaoListNewDeclaracao.setPedido(pedido);
                    declaracaoListNewDeclaracao = em.merge(declaracaoListNewDeclaracao);
                    if (oldPedidoOfDeclaracaoListNewDeclaracao != null && !oldPedidoOfDeclaracaoListNewDeclaracao.equals(pedido)) {
                        oldPedidoOfDeclaracaoListNewDeclaracao.getDeclaracaoList().remove(declaracaoListNewDeclaracao);
                        oldPedidoOfDeclaracaoListNewDeclaracao = em.merge(oldPedidoOfDeclaracaoListNewDeclaracao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = pedido.getId();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RealizacaoExame> realizacaoExameListOrphanCheck = pedido.getRealizacaoExameList();
            for (RealizacaoExame realizacaoExameListOrphanCheckRealizacaoExame : realizacaoExameListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the RealizacaoExame " + realizacaoExameListOrphanCheckRealizacaoExame + " in its realizacaoExameList field has a non-nullable pedido field.");
            }
            List<MudancaCurso> mudancaCursoListOrphanCheck = pedido.getMudancaCursoList();
            for (MudancaCurso mudancaCursoListOrphanCheckMudancaCurso : mudancaCursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the MudancaCurso " + mudancaCursoListOrphanCheckMudancaCurso + " in its mudancaCursoList field has a non-nullable pedido field.");
            }
            List<SituacaoDivida> situacaoDividaListOrphanCheck = pedido.getSituacaoDividaList();
            for (SituacaoDivida situacaoDividaListOrphanCheckSituacaoDivida : situacaoDividaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the SituacaoDivida " + situacaoDividaListOrphanCheckSituacaoDivida + " in its situacaoDividaList field has a non-nullable pedido field.");
            }
            List<TratamentoPedido> tratamentoPedidoListOrphanCheck = pedido.getTratamentoPedidoList();
            for (TratamentoPedido tratamentoPedidoListOrphanCheckTratamentoPedido : tratamentoPedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the TratamentoPedido " + tratamentoPedidoListOrphanCheckTratamentoPedido + " in its tratamentoPedidoList field has a non-nullable pedido field.");
            }
            List<MatriculaForaEpoca> matriculaForaEpocaListOrphanCheck = pedido.getMatriculaForaEpocaList();
            for (MatriculaForaEpoca matriculaForaEpocaListOrphanCheckMatriculaForaEpoca : matriculaForaEpocaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the MatriculaForaEpoca " + matriculaForaEpocaListOrphanCheckMatriculaForaEpoca + " in its matriculaForaEpocaList field has a non-nullable pedido field.");
            }
            List<Movimento> movimentoListOrphanCheck = pedido.getMovimentoList();
            for (Movimento movimentoListOrphanCheckMovimento : movimentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the Movimento " + movimentoListOrphanCheckMovimento + " in its movimentoList field has a non-nullable pedido field.");
            }
            List<PedidosCertificados> pedidosCertificadosListOrphanCheck = pedido.getPedidosCertificadosList();
            for (PedidosCertificados pedidosCertificadosListOrphanCheckPedidosCertificados : pedidosCertificadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the PedidosCertificados " + pedidosCertificadosListOrphanCheckPedidosCertificados + " in its pedidosCertificadosList field has a non-nullable pedido field.");
            }
            List<TransferenciaCrs> transferenciaCrsListOrphanCheck = pedido.getTransferenciaCrsList();
            for (TransferenciaCrs transferenciaCrsListOrphanCheckTransferenciaCrs : transferenciaCrsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the TransferenciaCrs " + transferenciaCrsListOrphanCheckTransferenciaCrs + " in its transferenciaCrsList field has a non-nullable pedido field.");
            }
            List<ReintegracaoAcademica> reintegracaoAcademicaListOrphanCheck = pedido.getReintegracaoAcademicaList();
            for (ReintegracaoAcademica reintegracaoAcademicaListOrphanCheckReintegracaoAcademica : reintegracaoAcademicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the ReintegracaoAcademica " + reintegracaoAcademicaListOrphanCheckReintegracaoAcademica + " in its reintegracaoAcademicaList field has a non-nullable pedido field.");
            }
            List<Equivalencia> equivalenciaListOrphanCheck = pedido.getEquivalenciaList();
            for (Equivalencia equivalenciaListOrphanCheckEquivalencia : equivalenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the Equivalencia " + equivalenciaListOrphanCheckEquivalencia + " in its equivalenciaList field has a non-nullable pedido field.");
            }
            List<SubmissaoRelatorioEstagio> submissaoRelatorioEstagioListOrphanCheck = pedido.getSubmissaoRelatorioEstagioList();
            for (SubmissaoRelatorioEstagio submissaoRelatorioEstagioListOrphanCheckSubmissaoRelatorioEstagio : submissaoRelatorioEstagioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the SubmissaoRelatorioEstagio " + submissaoRelatorioEstagioListOrphanCheckSubmissaoRelatorioEstagio + " in its submissaoRelatorioEstagioList field has a non-nullable pedido field.");
            }
            List<SubmissaoMonografias> submissaoMonografiasListOrphanCheck = pedido.getSubmissaoMonografiasList();
            for (SubmissaoMonografias submissaoMonografiasListOrphanCheckSubmissaoMonografias : submissaoMonografiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the SubmissaoMonografias " + submissaoMonografiasListOrphanCheckSubmissaoMonografias + " in its submissaoMonografiasList field has a non-nullable pedido field.");
            }
            List<Declaracao> declaracaoListOrphanCheck = pedido.getDeclaracaoList();
            for (Declaracao declaracaoListOrphanCheckDeclaracao : declaracaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the Declaracao " + declaracaoListOrphanCheckDeclaracao + " in its declaracaoList field has a non-nullable pedido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estado estado = pedido.getEstado();
            if (estado != null) {
                estado.getPedidoList().remove(pedido);
                estado = em.merge(estado);
            }
            TipoPedido tipoPedido = pedido.getTipoPedido();
            if (tipoPedido != null) {
                tipoPedido.getPedidoList().remove(pedido);
                tipoPedido = em.merge(tipoPedido);
            }
            Utilizador utilizador = pedido.getUtilizador();
            if (utilizador != null) {
                utilizador.getPedidoList().remove(pedido);
                utilizador = em.merge(utilizador);
            }
            List<Pedido> pedidoList = pedido.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.getPedidoList().remove(pedido);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            List<Pedido> pedidoList1 = pedido.getPedidoList1();
            for (Pedido pedidoList1Pedido : pedidoList1) {
                pedidoList1Pedido.getPedidoList().remove(pedido);
                pedidoList1Pedido = em.merge(pedidoList1Pedido);
            }
            List<Pedido> pedidoList2 = pedido.getPedidoList2();
            for (Pedido pedidoList2Pedido : pedidoList2) {
                pedidoList2Pedido.getPedidoList().remove(pedido);
                pedidoList2Pedido = em.merge(pedidoList2Pedido);
            }
            List<Pedido> pedidoList3 = pedido.getPedidoList3();
            for (Pedido pedidoList3Pedido : pedidoList3) {
                pedidoList3Pedido.getPedidoList2().remove(pedido);
                pedidoList3Pedido = em.merge(pedidoList3Pedido);
            }
            List<Pedido> pedidoList4 = pedido.getPedidoList4();
            for (Pedido pedidoList4Pedido : pedidoList4) {
                pedidoList4Pedido.getPedidoList().remove(pedido);
                pedidoList4Pedido = em.merge(pedidoList4Pedido);
            }
            List<Pedido> pedidoList5 = pedido.getPedidoList5();
            for (Pedido pedidoList5Pedido : pedidoList5) {
                pedidoList5Pedido.getPedidoList4().remove(pedido);
                pedidoList5Pedido = em.merge(pedidoList5Pedido);
            }
            List<Pedido> pedidoList6 = pedido.getPedidoList6();
            for (Pedido pedidoList6Pedido : pedidoList6) {
                pedidoList6Pedido.getPedidoList().remove(pedido);
                pedidoList6Pedido = em.merge(pedidoList6Pedido);
            }
            List<Pedido> pedidoList7 = pedido.getPedidoList7();
            for (Pedido pedidoList7Pedido : pedidoList7) {
                pedidoList7Pedido.getPedidoList6().remove(pedido);
                pedidoList7Pedido = em.merge(pedidoList7Pedido);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

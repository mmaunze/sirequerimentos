/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Meldo Maunze
 */
@Entity
@Table(catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findById", query = "SELECT p FROM Pedido p WHERE p.id = :id"),
    @NamedQuery(name = "Pedido.findByAssunto", query = "SELECT p FROM Pedido p WHERE p.assunto = :assunto"),
    @NamedQuery(name = "Pedido.findByDataPedido", query = "SELECT p FROM Pedido p WHERE p.dataPedido = :dataPedido"),
    @NamedQuery(name = "Pedido.findByJustifiativa", query = "SELECT p FROM Pedido p WHERE p.justifiativa = :justifiativa")})
public class Pedido implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;

    /**
     *
     */
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String assunto;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPedido;

    /**
     *
     */
    @Column(length = 255)
    private String justifiativa;

    /**
     *
     */
    @JoinTable(name = "reclamacao_nota", joinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pedido> pedidoList;

    /**
     *
     */
    @ManyToMany(mappedBy = "pedidoList", fetch = FetchType.EAGER)
    private List<Pedido> pedidoList1;

    /**
     *
     */
    @JoinTable(name = "anulacao_matricula", joinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pedido> pedidoList2;

    /**
     *
     */
    @ManyToMany(mappedBy = "pedidoList2", fetch = FetchType.EAGER)
    private List<Pedido> pedidoList3;

    /**
     *
     */
    @JoinTable(name = "mudanca_pagamento_propinas", joinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pedido> pedidoList4;

    /**
     *
     */
    @ManyToMany(mappedBy = "pedidoList4", fetch = FetchType.EAGER)
    private List<Pedido> pedidoList5;

    /**
     *
     */
    @JoinTable(name = "cartao_estudante", joinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pedido> pedidoList6;

    /**
     *
     */
    @ManyToMany(mappedBy = "pedidoList6", fetch = FetchType.EAGER)
    private List<Pedido> pedidoList7;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<RealizacaoExame> realizacaoExameList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<MudancaCurso> mudancaCursoList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<SituacaoDivida> situacaoDividaList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<TratamentoPedido> tratamentoPedidoList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<MatriculaForaEpoca> matriculaForaEpocaList;

    /**
     *
     */
    @JoinColumn(name = "estado", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Estado estado;

    /**
     *
     */
    @JoinColumn(name = "tipo_pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoPedido tipoPedido;

    /**
     *
     */
    @JoinColumn(name = "utilizador", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Utilizador utilizador;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<Movimento> movimentoList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<PedidosCertificados> pedidosCertificadosList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<TransferenciaCrs> transferenciaCrsList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<ReintegracaoAcademica> reintegracaoAcademicaList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<Equivalencia> equivalenciaList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<SubmissaoRelatorioEstagio> submissaoRelatorioEstagioList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<SubmissaoMonografias> submissaoMonografiasList;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<Declaracao> declaracaoList;

    /**
     *
     */
    public Pedido() {
    }

    /**
     *
     * @param id
     */
    public Pedido(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param assunto
     */
    public Pedido(Long id, String assunto) {
        this.id = id;
        this.assunto = assunto;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getAssunto() {
        return assunto;
    }

    /**
     *
     * @param assunto
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     *
     * @return
     */
    public Date getDataPedido() {
        return dataPedido;
    }

    /**
     *
     * @param dataPedido
     */
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    /**
     *
     * @return
     */
    public String getJustifiativa() {
        return justifiativa;
    }

    /**
     *
     * @param justifiativa
     */
    public void setJustifiativa(String justifiativa) {
        this.justifiativa = justifiativa;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    /**
     *
     * @param pedidoList
     */
    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList1() {
        return pedidoList1;
    }

    /**
     *
     * @param pedidoList1
     */
    public void setPedidoList1(List<Pedido> pedidoList1) {
        this.pedidoList1 = pedidoList1;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList2() {
        return pedidoList2;
    }

    /**
     *
     * @param pedidoList2
     */
    public void setPedidoList2(List<Pedido> pedidoList2) {
        this.pedidoList2 = pedidoList2;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList3() {
        return pedidoList3;
    }

    /**
     *
     * @param pedidoList3
     */
    public void setPedidoList3(List<Pedido> pedidoList3) {
        this.pedidoList3 = pedidoList3;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList4() {
        return pedidoList4;
    }

    /**
     *
     * @param pedidoList4
     */
    public void setPedidoList4(List<Pedido> pedidoList4) {
        this.pedidoList4 = pedidoList4;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList5() {
        return pedidoList5;
    }

    /**
     *
     * @param pedidoList5
     */
    public void setPedidoList5(List<Pedido> pedidoList5) {
        this.pedidoList5 = pedidoList5;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList6() {
        return pedidoList6;
    }

    /**
     *
     * @param pedidoList6
     */
    public void setPedidoList6(List<Pedido> pedidoList6) {
        this.pedidoList6 = pedidoList6;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList7() {
        return pedidoList7;
    }

    /**
     *
     * @param pedidoList7
     */
    public void setPedidoList7(List<Pedido> pedidoList7) {
        this.pedidoList7 = pedidoList7;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<RealizacaoExame> getRealizacaoExameList() {
        return realizacaoExameList;
    }

    /**
     *
     * @param realizacaoExameList
     */
    public void setRealizacaoExameList(List<RealizacaoExame> realizacaoExameList) {
        this.realizacaoExameList = realizacaoExameList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MudancaCurso> getMudancaCursoList() {
        return mudancaCursoList;
    }

    /**
     *
     * @param mudancaCursoList
     */
    public void setMudancaCursoList(List<MudancaCurso> mudancaCursoList) {
        this.mudancaCursoList = mudancaCursoList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<SituacaoDivida> getSituacaoDividaList() {
        return situacaoDividaList;
    }

    /**
     *
     * @param situacaoDividaList
     */
    public void setSituacaoDividaList(List<SituacaoDivida> situacaoDividaList) {
        this.situacaoDividaList = situacaoDividaList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<TratamentoPedido> getTratamentoPedidoList() {
        return tratamentoPedidoList;
    }

    /**
     *
     * @param tratamentoPedidoList
     */
    public void setTratamentoPedidoList(List<TratamentoPedido> tratamentoPedidoList) {
        this.tratamentoPedidoList = tratamentoPedidoList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MatriculaForaEpoca> getMatriculaForaEpocaList() {
        return matriculaForaEpocaList;
    }

    /**
     *
     * @param matriculaForaEpocaList
     */
    public void setMatriculaForaEpocaList(List<MatriculaForaEpoca> matriculaForaEpocaList) {
        this.matriculaForaEpocaList = matriculaForaEpocaList;
    }

    /**
     *
     * @return
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    /**
     *
     * @param tipoPedido
     */
    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    /**
     *
     * @return
     */
    public Utilizador getUtilizador() {
        return utilizador;
    }

    /**
     *
     * @param utilizador
     */
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Movimento> getMovimentoList() {
        return movimentoList;
    }

    /**
     *
     * @param movimentoList
     */
    public void setMovimentoList(List<Movimento> movimentoList) {
        this.movimentoList = movimentoList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PedidosCertificados> getPedidosCertificadosList() {
        return pedidosCertificadosList;
    }

    /**
     *
     * @param pedidosCertificadosList
     */
    public void setPedidosCertificadosList(List<PedidosCertificados> pedidosCertificadosList) {
        this.pedidosCertificadosList = pedidosCertificadosList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<TransferenciaCrs> getTransferenciaCrsList() {
        return transferenciaCrsList;
    }

    /**
     *
     * @param transferenciaCrsList
     */
    public void setTransferenciaCrsList(List<TransferenciaCrs> transferenciaCrsList) {
        this.transferenciaCrsList = transferenciaCrsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<ReintegracaoAcademica> getReintegracaoAcademicaList() {
        return reintegracaoAcademicaList;
    }

    /**
     *
     * @param reintegracaoAcademicaList
     */
    public void setReintegracaoAcademicaList(List<ReintegracaoAcademica> reintegracaoAcademicaList) {
        this.reintegracaoAcademicaList = reintegracaoAcademicaList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Equivalencia> getEquivalenciaList() {
        return equivalenciaList;
    }

    /**
     *
     * @param equivalenciaList
     */
    public void setEquivalenciaList(List<Equivalencia> equivalenciaList) {
        this.equivalenciaList = equivalenciaList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<SubmissaoRelatorioEstagio> getSubmissaoRelatorioEstagioList() {
        return submissaoRelatorioEstagioList;
    }

    /**
     *
     * @param submissaoRelatorioEstagioList
     */
    public void setSubmissaoRelatorioEstagioList(List<SubmissaoRelatorioEstagio> submissaoRelatorioEstagioList) {
        this.submissaoRelatorioEstagioList = submissaoRelatorioEstagioList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<SubmissaoMonografias> getSubmissaoMonografiasList() {
        return submissaoMonografiasList;
    }

    /**
     *
     * @param submissaoMonografiasList
     */
    public void setSubmissaoMonografiasList(List<SubmissaoMonografias> submissaoMonografiasList) {
        this.submissaoMonografiasList = submissaoMonografiasList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Declaracao> getDeclaracaoList() {
        return declaracaoList;
    }

    /**
     *
     * @param declaracaoList
     */
    public void setDeclaracaoList(List<Declaracao> declaracaoList) {
        this.declaracaoList = declaracaoList;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "modelo.Pedido[ id=" + id + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Meldo Maunze
 */
@Entity
@Table(name = "submissao_relatorio_estagio", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubmissaoRelatorioEstagio.findAll", query = "SELECT s FROM SubmissaoRelatorioEstagio s"),
    @NamedQuery(name = "SubmissaoRelatorioEstagio.findById", query = "SELECT s FROM SubmissaoRelatorioEstagio s WHERE s.id = :id"),
    @NamedQuery(name = "SubmissaoRelatorioEstagio.findByDataSubmissao", query = "SELECT s FROM SubmissaoRelatorioEstagio s WHERE s.dataSubmissao = :dataSubmissao")})
public class SubmissaoRelatorioEstagio implements Serializable {

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
    @Column(name = "data_submissao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataSubmissao;

    /**
     *
     */
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    /**
     *
     */
    public SubmissaoRelatorioEstagio() {
    }

    /**
     *
     * @param id
     */
    public SubmissaoRelatorioEstagio(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param dataSubmissao
     */
    public SubmissaoRelatorioEstagio(Long id, Date dataSubmissao) {
        this.id = id;
        this.dataSubmissao = dataSubmissao;
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
    public Date getDataSubmissao() {
        return dataSubmissao;
    }

    /**
     *
     * @param dataSubmissao
     */
    public void setDataSubmissao(Date dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    /**
     *
     * @return
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     *
     * @param pedido
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
        if (!(object instanceof SubmissaoRelatorioEstagio)) {
            return false;
        }
        SubmissaoRelatorioEstagio other = (SubmissaoRelatorioEstagio) object;
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
        return "modelo.SubmissaoRelatorioEstagio[ id=" + id + " ]";
    }
    
}

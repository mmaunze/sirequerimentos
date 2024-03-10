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
@Table(name = "submissao_monografias", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubmissaoMonografias.findAll", query = "SELECT s FROM SubmissaoMonografias s"),
    @NamedQuery(name = "SubmissaoMonografias.findById", query = "SELECT s FROM SubmissaoMonografias s WHERE s.id = :id"),
    @NamedQuery(name = "SubmissaoMonografias.findByDataLimiteSubmissao", query = "SELECT s FROM SubmissaoMonografias s WHERE s.dataLimiteSubmissao = :dataLimiteSubmissao")})
public class SubmissaoMonografias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "data_limite_submissao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataLimiteSubmissao;
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    public SubmissaoMonografias() {
    }

    public SubmissaoMonografias(Long id) {
        this.id = id;
    }

    public SubmissaoMonografias(Long id, Date dataLimiteSubmissao) {
        this.id = id;
        this.dataLimiteSubmissao = dataLimiteSubmissao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataLimiteSubmissao() {
        return dataLimiteSubmissao;
    }

    public void setDataLimiteSubmissao(Date dataLimiteSubmissao) {
        this.dataLimiteSubmissao = dataLimiteSubmissao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubmissaoMonografias)) {
            return false;
        }
        SubmissaoMonografias other = (SubmissaoMonografias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.SubmissaoMonografias[ id=" + id + " ]";
    }
    
}

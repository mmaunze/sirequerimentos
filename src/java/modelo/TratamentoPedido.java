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
@Table(name = "tratamento_pedido", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TratamentoPedido.findAll", query = "SELECT t FROM TratamentoPedido t"),
    @NamedQuery(name = "TratamentoPedido.findById", query = "SELECT t FROM TratamentoPedido t WHERE t.id = :id"),
    @NamedQuery(name = "TratamentoPedido.findByDataTratamento", query = "SELECT t FROM TratamentoPedido t WHERE t.dataTratamento = :dataTratamento"),
    @NamedQuery(name = "TratamentoPedido.findByObservacao", query = "SELECT t FROM TratamentoPedido t WHERE t.observacao = :observacao")})
public class TratamentoPedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(name = "data_tratamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTratamento;
    @Column(length = 255)
    private String observacao;
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;
    @JoinColumn(name = "secretario", referencedColumnName = "utilizador", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Secretario secretario;

    public TratamentoPedido() {
    }

    public TratamentoPedido(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataTratamento() {
        return dataTratamento;
    }

    public void setDataTratamento(Date dataTratamento) {
        this.dataTratamento = dataTratamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Secretario getSecretario() {
        return secretario;
    }

    public void setSecretario(Secretario secretario) {
        this.secretario = secretario;
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
        if (!(object instanceof TratamentoPedido)) {
            return false;
        }
        TratamentoPedido other = (TratamentoPedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TratamentoPedido[ id=" + id + " ]";
    }
    
}

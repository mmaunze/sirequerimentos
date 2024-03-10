/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Meldo Maunze
 */
@Entity
@Table(name = "situacao_divida", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SituacaoDivida.findAll", query = "SELECT s FROM SituacaoDivida s"),
    @NamedQuery(name = "SituacaoDivida.findById", query = "SELECT s FROM SituacaoDivida s WHERE s.id = :id"),
    @NamedQuery(name = "SituacaoDivida.findByValorDivida", query = "SELECT s FROM SituacaoDivida s WHERE s.valorDivida = :valorDivida")})
public class SituacaoDivida implements Serializable {

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    /**
     *
     */
    @Basic(optional = false)
    @Column(name = "valor_divida", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDivida;

    /**
     *
     */
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    /**
     *
     */
    public SituacaoDivida() {
    }

    /**
     *
     * @param id
     */
    public SituacaoDivida(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param valorDivida
     */
    public SituacaoDivida(Long id, BigDecimal valorDivida) {
        this.id = id;
        this.valorDivida = valorDivida;
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
    public BigDecimal getValorDivida() {
        return valorDivida;
    }

    /**
     *
     * @param valorDivida
     */
    public void setValorDivida(BigDecimal valorDivida) {
        this.valorDivida = valorDivida;
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
        if (!(object instanceof SituacaoDivida)) {
            return false;
        }
        SituacaoDivida other = (SituacaoDivida) object;
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
        return "modelo.SituacaoDivida[ id=" + id + " ]";
    }
    
}

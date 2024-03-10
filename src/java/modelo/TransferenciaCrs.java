/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
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
@Table(name = "transferencia_crs", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransferenciaCrs.findAll", query = "SELECT t FROM TransferenciaCrs t"),
    @NamedQuery(name = "TransferenciaCrs.findById", query = "SELECT t FROM TransferenciaCrs t WHERE t.id = :id"),
    @NamedQuery(name = "TransferenciaCrs.findByDestinoCrs", query = "SELECT t FROM TransferenciaCrs t WHERE t.destinoCrs = :destinoCrs")})
public class TransferenciaCrs implements Serializable {

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
    @Column(name = "destino_crs", nullable = false, length = 100)
    private String destinoCrs;

    /**
     *
     */
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    /**
     *
     */
    public TransferenciaCrs() {
    }

    /**
     *
     * @param id
     */
    public TransferenciaCrs(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param destinoCrs
     */
    public TransferenciaCrs(Long id, String destinoCrs) {
        this.id = id;
        this.destinoCrs = destinoCrs;
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
    public String getDestinoCrs() {
        return destinoCrs;
    }

    /**
     *
     * @param destinoCrs
     */
    public void setDestinoCrs(String destinoCrs) {
        this.destinoCrs = destinoCrs;
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
        if (!(object instanceof TransferenciaCrs)) {
            return false;
        }
        TransferenciaCrs other = (TransferenciaCrs) object;
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
        return "modelo.TransferenciaCrs[ id=" + id + " ]";
    }
    
}

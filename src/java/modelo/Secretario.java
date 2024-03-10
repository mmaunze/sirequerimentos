/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    @NamedQuery(name = "Secretario.findAll", query = "SELECT s FROM Secretario s"),
    @NamedQuery(name = "Secretario.findByUtilizador", query = "SELECT s FROM Secretario s WHERE s.utilizador = :utilizador")})
public class Secretario implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long utilizador;

    /**
     *
     */
    @JoinColumn(name = "faculdade", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Faculdade faculdade;

    /**
     *
     */
    @JoinColumn(name = "utilizador", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Utilizador utilizador1;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secretario", fetch = FetchType.EAGER)
    private List<TratamentoPedido> tratamentoPedidoList;

    /**
     *
     */
    public Secretario() {
    }

    /**
     *
     * @param utilizador
     */
    public Secretario(Long utilizador) {
        this.utilizador = utilizador;
    }

    /**
     *
     * @return
     */
    public Long getUtilizador() {
        return utilizador;
    }

    /**
     *
     * @param utilizador
     */
    public void setUtilizador(Long utilizador) {
        this.utilizador = utilizador;
    }

    /**
     *
     * @return
     */
    public Faculdade getFaculdade() {
        return faculdade;
    }

    /**
     *
     * @param faculdade
     */
    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    /**
     *
     * @return
     */
    public Utilizador getUtilizador1() {
        return utilizador1;
    }

    /**
     *
     * @param utilizador1
     */
    public void setUtilizador1(Utilizador utilizador1) {
        this.utilizador1 = utilizador1;
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
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utilizador != null ? utilizador.hashCode() : 0);
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
        if (!(object instanceof Secretario)) {
            return false;
        }
        Secretario other = (Secretario) object;
        if ((this.utilizador == null && other.utilizador != null) || (this.utilizador != null && !this.utilizador.equals(other.utilizador))) {
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
        return "modelo.Secretario[ utilizador=" + utilizador + " ]";
    }
    
}

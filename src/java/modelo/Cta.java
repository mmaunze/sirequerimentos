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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Meldo Maunze
 */
@Entity
@Table(catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cta.findAll", query = "SELECT c FROM Cta c"),
    @NamedQuery(name = "Cta.findByUtilizador", query = "SELECT c FROM Cta c WHERE c.utilizador = :utilizador"),
    @NamedQuery(name = "Cta.findByGrau", query = "SELECT c FROM Cta c WHERE c.grau = :grau")})
public class Cta implements Serializable {

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
    @Column(length = 50)
    private String grau;

    /**
     *
     */
    @JoinColumn(name = "cargo", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cargo cargo;

    /**
     *
     */
    @JoinColumn(name = "utilizador", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Utilizador utilizador1;

    /**
     *
     */
    public Cta() {
    }

    /**
     *
     * @param utilizador
     */
    public Cta(Long utilizador) {
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
    public String getGrau() {
        return grau;
    }

    /**
     *
     * @param grau
     */
    public void setGrau(String grau) {
        this.grau = grau;
    }

    /**
     *
     * @return
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     *
     * @param cargo
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof Cta)) {
            return false;
        }
        Cta other = (Cta) object;
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
        return "modelo.Cta[ utilizador=" + utilizador + " ]";
    }
    
}

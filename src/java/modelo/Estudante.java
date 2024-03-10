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
    @NamedQuery(name = "Estudante.findAll", query = "SELECT e FROM Estudante e"),
    @NamedQuery(name = "Estudante.findByUtilizador", query = "SELECT e FROM Estudante e WHERE e.utilizador = :utilizador"),
    @NamedQuery(name = "Estudante.findByNivel", query = "SELECT e FROM Estudante e WHERE e.nivel = :nivel")})
public class Estudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long utilizador;
    @Basic(optional = false)
    @Column(nullable = false)
    private long nivel;
    @JoinColumn(name = "curso", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Curso curso;
    @JoinColumn(name = "utilizador", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Utilizador utilizador1;

    public Estudante() {
    }

    public Estudante(Long utilizador) {
        this.utilizador = utilizador;
    }

    public Estudante(Long utilizador, long nivel) {
        this.utilizador = utilizador;
        this.nivel = nivel;
    }

    public Long getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Long utilizador) {
        this.utilizador = utilizador;
    }

    public long getNivel() {
        return nivel;
    }

    public void setNivel(long nivel) {
        this.nivel = nivel;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Utilizador getUtilizador1() {
        return utilizador1;
    }

    public void setUtilizador1(Utilizador utilizador1) {
        this.utilizador1 = utilizador1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utilizador != null ? utilizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudante)) {
            return false;
        }
        Estudante other = (Estudante) object;
        if ((this.utilizador == null && other.utilizador != null) || (this.utilizador != null && !this.utilizador.equals(other.utilizador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Estudante[ utilizador=" + utilizador + " ]";
    }
    
}

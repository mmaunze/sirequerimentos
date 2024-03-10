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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Meldo Maunze
 */
@Entity
@Table(catalog = "sistemarequerimentos", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sigla"}),
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faculdade.findAll", query = "SELECT f FROM Faculdade f"),
    @NamedQuery(name = "Faculdade.findById", query = "SELECT f FROM Faculdade f WHERE f.id = :id"),
    @NamedQuery(name = "Faculdade.findByDescricao", query = "SELECT f FROM Faculdade f WHERE f.descricao = :descricao"),
    @NamedQuery(name = "Faculdade.findBySigla", query = "SELECT f FROM Faculdade f WHERE f.sigla = :sigla")})
public class Faculdade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String descricao;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String sigla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faculdade", fetch = FetchType.EAGER)
    private List<Secretario> secretarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faculdade", fetch = FetchType.EAGER)
    private List<Curso> cursoList;

    public Faculdade() {
    }

    public Faculdade(Long id) {
        this.id = id;
    }

    public Faculdade(Long id, String descricao, String sigla) {
        this.id = id;
        this.descricao = descricao;
        this.sigla = sigla;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @XmlTransient
    public List<Secretario> getSecretarioList() {
        return secretarioList;
    }

    public void setSecretarioList(List<Secretario> secretarioList) {
        this.secretarioList = secretarioList;
    }

    @XmlTransient
    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
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
        if (!(object instanceof Faculdade)) {
            return false;
        }
        Faculdade other = (Faculdade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Faculdade[ id=" + id + " ]";
    }
    
}

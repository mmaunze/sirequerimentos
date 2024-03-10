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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findById", query = "SELECT c FROM Curso c WHERE c.id = :id"),
    @NamedQuery(name = "Curso.findByDescricao", query = "SELECT c FROM Curso c WHERE c.descricao = :descricao"),
    @NamedQuery(name = "Curso.findBySigla", query = "SELECT c FROM Curso c WHERE c.sigla = :sigla")})
public class Curso implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursoDestino", fetch = FetchType.EAGER)
    private List<MudancaCurso> mudancaCursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cusoOrigem", fetch = FetchType.EAGER)
    private List<MudancaCurso> mudancaCursoList1;
    @JoinColumn(name = "faculdade", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Faculdade faculdade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso", fetch = FetchType.EAGER)
    private List<Estudante> estudanteList;

    public Curso() {
    }

    public Curso(Long id) {
        this.id = id;
    }

    public Curso(Long id, String descricao, String sigla) {
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
    public List<MudancaCurso> getMudancaCursoList() {
        return mudancaCursoList;
    }

    public void setMudancaCursoList(List<MudancaCurso> mudancaCursoList) {
        this.mudancaCursoList = mudancaCursoList;
    }

    @XmlTransient
    public List<MudancaCurso> getMudancaCursoList1() {
        return mudancaCursoList1;
    }

    public void setMudancaCursoList1(List<MudancaCurso> mudancaCursoList1) {
        this.mudancaCursoList1 = mudancaCursoList1;
    }

    public Faculdade getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    @XmlTransient
    public List<Estudante> getEstudanteList() {
        return estudanteList;
    }

    public void setEstudanteList(List<Estudante> estudanteList) {
        this.estudanteList = estudanteList;
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
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Curso[ id=" + id + " ]";
    }
    
}

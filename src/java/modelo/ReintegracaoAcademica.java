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
@Table(name = "reintegracao_academica", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReintegracaoAcademica.findAll", query = "SELECT r FROM ReintegracaoAcademica r"),
    @NamedQuery(name = "ReintegracaoAcademica.findById", query = "SELECT r FROM ReintegracaoAcademica r WHERE r.id = :id"),
    @NamedQuery(name = "ReintegracaoAcademica.findByAnoAcademico", query = "SELECT r FROM ReintegracaoAcademica r WHERE r.anoAcademico = :anoAcademico")})
public class ReintegracaoAcademica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "ano_academico", nullable = false, length = 10)
    private String anoAcademico;
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    public ReintegracaoAcademica() {
    }

    public ReintegracaoAcademica(Long id) {
        this.id = id;
    }

    public ReintegracaoAcademica(Long id, String anoAcademico) {
        this.id = id;
        this.anoAcademico = anoAcademico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnoAcademico() {
        return anoAcademico;
    }

    public void setAnoAcademico(String anoAcademico) {
        this.anoAcademico = anoAcademico;
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
        if (!(object instanceof ReintegracaoAcademica)) {
            return false;
        }
        ReintegracaoAcademica other = (ReintegracaoAcademica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ReintegracaoAcademica[ id=" + id + " ]";
    }
    
}

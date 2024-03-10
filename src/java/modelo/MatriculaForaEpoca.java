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
@Table(name = "matricula_fora_epoca", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatriculaForaEpoca.findAll", query = "SELECT m FROM MatriculaForaEpoca m"),
    @NamedQuery(name = "MatriculaForaEpoca.findById", query = "SELECT m FROM MatriculaForaEpoca m WHERE m.id = :id"),
    @NamedQuery(name = "MatriculaForaEpoca.findByAnoMatricula", query = "SELECT m FROM MatriculaForaEpoca m WHERE m.anoMatricula = :anoMatricula")})
public class MatriculaForaEpoca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "ano_matricula", nullable = false)
    private long anoMatricula;
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    public MatriculaForaEpoca() {
    }

    public MatriculaForaEpoca(Long id) {
        this.id = id;
    }

    public MatriculaForaEpoca(Long id, long anoMatricula) {
        this.id = id;
        this.anoMatricula = anoMatricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAnoMatricula() {
        return anoMatricula;
    }

    public void setAnoMatricula(long anoMatricula) {
        this.anoMatricula = anoMatricula;
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
        if (!(object instanceof MatriculaForaEpoca)) {
            return false;
        }
        MatriculaForaEpoca other = (MatriculaForaEpoca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MatriculaForaEpoca[ id=" + id + " ]";
    }
    
}

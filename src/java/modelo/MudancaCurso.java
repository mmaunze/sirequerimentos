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
@Table(name = "mudanca_curso", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MudancaCurso.findAll", query = "SELECT m FROM MudancaCurso m"),
    @NamedQuery(name = "MudancaCurso.findById", query = "SELECT m FROM MudancaCurso m WHERE m.id = :id")})
public class MudancaCurso implements Serializable {

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
    @JoinColumn(name = "curso_destino", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Curso cursoDestino;

    /**
     *
     */
    @JoinColumn(name = "cuso_origem", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Curso cusoOrigem;

    /**
     *
     */
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    /**
     *
     */
    public MudancaCurso() {
    }

    /**
     *
     * @param id
     */
    public MudancaCurso(Long id) {
        this.id = id;
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
    public Curso getCursoDestino() {
        return cursoDestino;
    }

    /**
     *
     * @param cursoDestino
     */
    public void setCursoDestino(Curso cursoDestino) {
        this.cursoDestino = cursoDestino;
    }

    /**
     *
     * @return
     */
    public Curso getCusoOrigem() {
        return cusoOrigem;
    }

    /**
     *
     * @param cusoOrigem
     */
    public void setCusoOrigem(Curso cusoOrigem) {
        this.cusoOrigem = cusoOrigem;
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
        if (!(object instanceof MudancaCurso)) {
            return false;
        }
        MudancaCurso other = (MudancaCurso) object;
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
        return "modelo.MudancaCurso[ id=" + id + " ]";
    }
    
}

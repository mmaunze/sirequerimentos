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
@Table(catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equivalencia.findAll", query = "SELECT e FROM Equivalencia e"),
    @NamedQuery(name = "Equivalencia.findById", query = "SELECT e FROM Equivalencia e WHERE e.id = :id"),
    @NamedQuery(name = "Equivalencia.findByDisciplinaOrigem", query = "SELECT e FROM Equivalencia e WHERE e.disciplinaOrigem = :disciplinaOrigem"),
    @NamedQuery(name = "Equivalencia.findByDisciplinaDestino", query = "SELECT e FROM Equivalencia e WHERE e.disciplinaDestino = :disciplinaDestino")})
public class Equivalencia implements Serializable {

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
    @Column(name = "disciplina_origem", nullable = false, length = 255)
    private String disciplinaOrigem;

    /**
     *
     */
    @Basic(optional = false)
    @Column(name = "disciplina_destino", nullable = false, length = 255)
    private String disciplinaDestino;

    /**
     *
     */
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    /**
     *
     */
    public Equivalencia() {
    }

    /**
     *
     * @param id
     */
    public Equivalencia(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param disciplinaOrigem
     * @param disciplinaDestino
     */
    public Equivalencia(Long id, String disciplinaOrigem, String disciplinaDestino) {
        this.id = id;
        this.disciplinaOrigem = disciplinaOrigem;
        this.disciplinaDestino = disciplinaDestino;
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
    public String getDisciplinaOrigem() {
        return disciplinaOrigem;
    }

    /**
     *
     * @param disciplinaOrigem
     */
    public void setDisciplinaOrigem(String disciplinaOrigem) {
        this.disciplinaOrigem = disciplinaOrigem;
    }

    /**
     *
     * @return
     */
    public String getDisciplinaDestino() {
        return disciplinaDestino;
    }

    /**
     *
     * @param disciplinaDestino
     */
    public void setDisciplinaDestino(String disciplinaDestino) {
        this.disciplinaDestino = disciplinaDestino;
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
        if (!(object instanceof Equivalencia)) {
            return false;
        }
        Equivalencia other = (Equivalencia) object;
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
        return "modelo.Equivalencia[ id=" + id + " ]";
    }
    
}

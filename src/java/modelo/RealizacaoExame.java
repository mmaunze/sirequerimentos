/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Meldo Maunze
 */
@Entity
@Table(name = "realizacao_exame", catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RealizacaoExame.findAll", query = "SELECT r FROM RealizacaoExame r"),
    @NamedQuery(name = "RealizacaoExame.findById", query = "SELECT r FROM RealizacaoExame r WHERE r.id = :id"),
    @NamedQuery(name = "RealizacaoExame.findByTipoExame", query = "SELECT r FROM RealizacaoExame r WHERE r.tipoExame = :tipoExame"),
    @NamedQuery(name = "RealizacaoExame.findByDataExame", query = "SELECT r FROM RealizacaoExame r WHERE r.dataExame = :dataExame")})
public class RealizacaoExame implements Serializable {

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
    @Column(name = "tipo_exame", nullable = false, length = 100)
    private String tipoExame;

    /**
     *
     */
    @Basic(optional = false)
    @Column(name = "data_exame", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataExame;

    /**
     *
     */
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pedido pedido;

    /**
     *
     */
    public RealizacaoExame() {
    }

    /**
     *
     * @param id
     */
    public RealizacaoExame(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param tipoExame
     * @param dataExame
     */
    public RealizacaoExame(Long id, String tipoExame, Date dataExame) {
        this.id = id;
        this.tipoExame = tipoExame;
        this.dataExame = dataExame;
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
    public String getTipoExame() {
        return tipoExame;
    }

    /**
     *
     * @param tipoExame
     */
    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    /**
     *
     * @return
     */
    public Date getDataExame() {
        return dataExame;
    }

    /**
     *
     * @param dataExame
     */
    public void setDataExame(Date dataExame) {
        this.dataExame = dataExame;
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
        if (!(object instanceof RealizacaoExame)) {
            return false;
        }
        RealizacaoExame other = (RealizacaoExame) object;
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
        return "modelo.RealizacaoExame[ id=" + id + " ]";
    }
    
}

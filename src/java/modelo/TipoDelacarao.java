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
@Table(name = "tipo_delacarao", catalog = "sistemarequerimentos", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDelacarao.findAll", query = "SELECT t FROM TipoDelacarao t"),
    @NamedQuery(name = "TipoDelacarao.findById", query = "SELECT t FROM TipoDelacarao t WHERE t.id = :id"),
    @NamedQuery(name = "TipoDelacarao.findByDescricao", query = "SELECT t FROM TipoDelacarao t WHERE t.descricao = :descricao")})
public class TipoDelacarao implements Serializable {

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
    @Column(length = 100)
    private String descricao;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDelacarao", fetch = FetchType.EAGER)
    private List<Declaracao> declaracaoList;

    /**
     *
     */
    public TipoDelacarao() {
    }

    /**
     *
     * @param id
     */
    public TipoDelacarao(Long id) {
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
    public String getDescricao() {
        return descricao;
    }

    /**
     *
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Declaracao> getDeclaracaoList() {
        return declaracaoList;
    }

    /**
     *
     * @param declaracaoList
     */
    public void setDeclaracaoList(List<Declaracao> declaracaoList) {
        this.declaracaoList = declaracaoList;
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
        if (!(object instanceof TipoDelacarao)) {
            return false;
        }
        TipoDelacarao other = (TipoDelacarao) object;
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
        return "modelo.TipoDelacarao[ id=" + id + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Meldo Maunze
 */
@Entity
@Table(catalog = "sistemarequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instituicao.findAll", query = "SELECT i FROM Instituicao i"),
    @NamedQuery(name = "Instituicao.findByIdInstituicao", query = "SELECT i FROM Instituicao i WHERE i.idInstituicao = :idInstituicao"),
    @NamedQuery(name = "Instituicao.findByEndereco", query = "SELECT i FROM Instituicao i WHERE i.endereco = :endereco"),
    @NamedQuery(name = "Instituicao.findByLema", query = "SELECT i FROM Instituicao i WHERE i.lema = :lema"),
    @NamedQuery(name = "Instituicao.findByNome", query = "SELECT i FROM Instituicao i WHERE i.nome = :nome")})
public class Instituicao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_instituicao", nullable = false)
    private Integer idInstituicao;
    @Column(length = 255)
    private String endereco;
    @Column(length = 255)
    private String lema;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String nome;
    @OneToMany(mappedBy = "idInstituicao", fetch = FetchType.EAGER)
    private List<Usuario> usuarioList;

    public Instituicao() {
    }

    public Instituicao(Integer idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public Instituicao(Integer idInstituicao, String nome) {
        this.idInstituicao = idInstituicao;
        this.nome = nome;
    }

    public Integer getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Integer idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInstituicao != null ? idInstituicao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instituicao)) {
            return false;
        }
        Instituicao other = (Instituicao) object;
        if ((this.idInstituicao == null && other.idInstituicao != null) || (this.idInstituicao != null && !this.idInstituicao.equals(other.idInstituicao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Instituicao[ idInstituicao=" + idInstituicao + " ]";
    }
    
}

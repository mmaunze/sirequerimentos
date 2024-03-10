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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @UniqueConstraint(columnNames = {"contacto"}),
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"username"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilizador.findAll", query = "SELECT u FROM Utilizador u"),
    @NamedQuery(name = "Utilizador.findById", query = "SELECT u FROM Utilizador u WHERE u.id = :id"),
    @NamedQuery(name = "Utilizador.findByNome", query = "SELECT u FROM Utilizador u WHERE u.nome = :nome"),
    @NamedQuery(name = "Utilizador.findByContacto", query = "SELECT u FROM Utilizador u WHERE u.contacto = :contacto"),
    @NamedQuery(name = "Utilizador.findByEmail", query = "SELECT u FROM Utilizador u WHERE u.email = :email"),
    @NamedQuery(name = "Utilizador.findBySexo", query = "SELECT u FROM Utilizador u WHERE u.sexo = :sexo"),
    @NamedQuery(name = "Utilizador.findByUsername", query = "SELECT u FROM Utilizador u WHERE u.username = :username"),
    @NamedQuery(name = "Utilizador.findBySenha", query = "SELECT u FROM Utilizador u WHERE u.senha = :senha")})
public class Utilizador implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;

    /**
     *
     */
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String nome;

    /**
     *
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private long contacto;

    /**
     *
     */
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String email;

    /**
     *
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private Character sexo;

    /**
     *
     */
    @Column(length = 70)
    private String username;

    /**
     *
     */
    @Column(length = 70)
    private String senha;

    /**
     *
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "utilizador1", fetch = FetchType.EAGER)
    private Secretario secretario;

    /**
     *
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "utilizador1", fetch = FetchType.EAGER)
    private Cta cta;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilizador", fetch = FetchType.EAGER)
    private List<Pedido> pedidoList;

    /**
     *
     */
    @JoinColumn(name = "departamento", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Departamento departamento;

    /**
     *
     */
    @JoinColumn(name = "tipo_utilizador", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoUtilizador tipoUtilizador;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilizador", fetch = FetchType.EAGER)
    private List<Movimento> movimentoList;

    /**
     *
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "utilizador1", fetch = FetchType.EAGER)
    private Estudante estudante;

    /**
     *
     */
    public Utilizador() {
    }

    /**
     *
     * @param id
     */
    public Utilizador(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param nome
     * @param contacto
     * @param email
     * @param sexo
     */
    public Utilizador(Long id, String nome, long contacto, String email, Character sexo) {
        this.id = id;
        this.nome = nome;
        this.contacto = contacto;
        this.email = email;
        this.sexo = sexo;
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
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return
     */
    public long getContacto() {
        return contacto;
    }

    /**
     *
     * @param contacto
     */
    public void setContacto(long contacto) {
        this.contacto = contacto;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public Character getSexo() {
        return sexo;
    }

    /**
     *
     * @param sexo
     */
    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getSenha() {
        return senha;
    }

    /**
     *
     * @param senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     *
     * @return
     */
    public Secretario getSecretario() {
        return secretario;
    }

    /**
     *
     * @param secretario
     */
    public void setSecretario(Secretario secretario) {
        this.secretario = secretario;
    }

    /**
     *
     * @return
     */
    public Cta getCta() {
        return cta;
    }

    /**
     *
     * @param cta
     */
    public void setCta(Cta cta) {
        this.cta = cta;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    /**
     *
     * @param pedidoList
     */
    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    /**
     *
     * @return
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     *
     * @param departamento
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     *
     * @return
     */
    public TipoUtilizador getTipoUtilizador() {
        return tipoUtilizador;
    }

    /**
     *
     * @param tipoUtilizador
     */
    public void setTipoUtilizador(TipoUtilizador tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Movimento> getMovimentoList() {
        return movimentoList;
    }

    /**
     *
     * @param movimentoList
     */
    public void setMovimentoList(List<Movimento> movimentoList) {
        this.movimentoList = movimentoList;
    }

    /**
     *
     * @return
     */
    public Estudante getEstudante() {
        return estudante;
    }

    /**
     *
     * @param estudante
     */
    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
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
        if (!(object instanceof Utilizador)) {
            return false;
        }
        Utilizador other = (Utilizador) object;
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
        return "modelo.Utilizador[ id=" + id + " ]";
    }
    
}

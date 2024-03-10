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
@Table(name = "mudanca_pagamento_propinas", catalog = "SistemaRequerimentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MudancaPagamentoPropinas.findAll", query = "SELECT m FROM MudancaPagamentoPropinas m"),
    @NamedQuery(name = "MudancaPagamentoPropinas.findById", query = "SELECT m FROM MudancaPagamentoPropinas m WHERE m.id = :id")})
public class MudancaPagamentoPropinas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "pedido", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pedido pedido;

    public MudancaPagamentoPropinas() {
    }

    public MudancaPagamentoPropinas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof MudancaPagamentoPropinas)) {
            return false;
        }
        MudancaPagamentoPropinas other = (MudancaPagamentoPropinas) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "modelo.MudancaPagamentoPropinas[ id=" + id + " ]";
    }
    
}

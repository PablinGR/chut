/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chut.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pablingr
 */
@Entity
@Table(name = "JUGADORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugadores.findAll", query = "SELECT j FROM Jugadores j")
    , @NamedQuery(name = "Jugadores.findByCedulajugador", query = "SELECT j FROM Jugadores j WHERE j.cedulajugador = :cedulajugador")
    , @NamedQuery(name = "Jugadores.findByNombrejugador", query = "SELECT j FROM Jugadores j WHERE j.nombrejugador = :nombrejugador")
    , @NamedQuery(name = "Jugadores.findByCelularjugador", query = "SELECT j FROM Jugadores j WHERE j.celularjugador = :celularjugador")
    , @NamedQuery(name = "Jugadores.findByMailjugador", query = "SELECT j FROM Jugadores j WHERE j.mailjugador = :mailjugador")})
public class Jugadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CEDULAJUGADOR")
    private String cedulajugador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBREJUGADOR")
    private String nombrejugador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CELULARJUGADOR")
    private BigInteger celularjugador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MAILJUGADOR")
    private String mailjugador;
    @JoinColumn(name = "IDEQUIPO", referencedColumnName = "IDEQUIPO")
    @ManyToOne(optional = false)
    private Equipos idequipo;

    public Jugadores() {
    }

    public Jugadores(String cedulajugador) {
        this.cedulajugador = cedulajugador;
    }

    public Jugadores(String cedulajugador, String nombrejugador, BigInteger celularjugador, String mailjugador) {
        this.cedulajugador = cedulajugador;
        this.nombrejugador = nombrejugador;
        this.celularjugador = celularjugador;
        this.mailjugador = mailjugador;
    }

    public String getCedulajugador() {
        return cedulajugador;
    }

    public void setCedulajugador(String cedulajugador) {
        this.cedulajugador = cedulajugador;
    }

    public String getNombrejugador() {
        return nombrejugador;
    }

    public void setNombrejugador(String nombrejugador) {
        this.nombrejugador = nombrejugador;
    }

    public BigInteger getCelularjugador() {
        return celularjugador;
    }

    public void setCelularjugador(BigInteger celularjugador) {
        this.celularjugador = celularjugador;
    }

    public String getMailjugador() {
        return mailjugador;
    }

    public void setMailjugador(String mailjugador) {
        this.mailjugador = mailjugador;
    }

    public Equipos getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Equipos idequipo) {
        this.idequipo = idequipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedulajugador != null ? cedulajugador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugadores)) {
            return false;
        }
        Jugadores other = (Jugadores) object;
        if ((this.cedulajugador == null && other.cedulajugador != null) || (this.cedulajugador != null && !this.cedulajugador.equals(other.cedulajugador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.chut.entidades.Jugadores[ cedulajugador=" + cedulajugador + " ]";
    }
    
}

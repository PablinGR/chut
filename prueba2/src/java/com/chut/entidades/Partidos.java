/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chut.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pablingr
 */
@Entity
@Table(name = "PARTIDOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partidos.findAll", query = "SELECT p FROM Partidos p")
    , @NamedQuery(name = "Partidos.findByIdpartido", query = "SELECT p FROM Partidos p WHERE p.idpartido = :idpartido")
    , @NamedQuery(name = "Partidos.findByFechapartido", query = "SELECT p FROM Partidos p WHERE p.fechapartido = :fechapartido")})
public class Partidos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPARTIDO")
    private BigDecimal idpartido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAPARTIDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapartido;
    @JoinColumn(name = "IDCANCHA", referencedColumnName = "IDCANCHA")
    @ManyToOne(optional = false)
    private Canchas idcancha;
    @JoinColumn(name = "IDEQUIPO", referencedColumnName = "IDEQUIPO")
    @ManyToOne(optional = false)
    private Equipos idequipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpartido")
    private Collection<Reservas> reservasCollection;

    public Partidos() {
    }

    public Partidos(BigDecimal idpartido) {
        this.idpartido = idpartido;
    }

    public Partidos(BigDecimal idpartido, Date fechapartido) {
        this.idpartido = idpartido;
        this.fechapartido = fechapartido;
    }

    public BigDecimal getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(BigDecimal idpartido) {
        this.idpartido = idpartido;
    }

    public Date getFechapartido() {
        return fechapartido;
    }

    public void setFechapartido(Date fechapartido) {
        this.fechapartido = fechapartido;
    }

    public Canchas getIdcancha() {
        return idcancha;
    }

    public void setIdcancha(Canchas idcancha) {
        this.idcancha = idcancha;
    }

    public Equipos getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Equipos idequipo) {
        this.idequipo = idequipo;
    }

    @XmlTransient
    public Collection<Reservas> getReservasCollection() {
        return reservasCollection;
    }

    public void setReservasCollection(Collection<Reservas> reservasCollection) {
        this.reservasCollection = reservasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpartido != null ? idpartido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partidos)) {
            return false;
        }
        Partidos other = (Partidos) object;
        if ((this.idpartido == null && other.idpartido != null) || (this.idpartido != null && !this.idpartido.equals(other.idpartido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.chut.entidades.Partidos[ idpartido=" + idpartido + " ]";
    }
    
}

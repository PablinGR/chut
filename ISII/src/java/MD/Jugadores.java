/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "JUGADORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugadores.findAll", query = "SELECT j FROM Jugadores j")
    , @NamedQuery(name = "Jugadores.findByCedulajugador", query = "SELECT j FROM Jugadores j WHERE j.cedulajugador = :cedulajugador")
    , @NamedQuery(name = "Jugadores.findByNombrejugador", query = "SELECT j FROM Jugadores j WHERE j.nombrejugador = :nombrejugador")
    , @NamedQuery(name = "Jugadores.findByCelularjugador", query = "SELECT j FROM Jugadores j WHERE j.celularjugador = :celularjugador")
    , @NamedQuery(name = "Jugadores.findByMailjugador", query = "SELECT j FROM Jugadores j WHERE j.mailjugador = :mailjugador")
    , @NamedQuery(name = "Jugadores.findByCapitan", query = "SELECT j FROM Jugadores j WHERE j.capitan = :capitan")})
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
    private long celularjugador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MAILJUGADOR")
    private String mailjugador;
    @Column(name = "CAPITAN")
    private Boolean capitan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cedulajugador")
    private Collection<Equipos> equiposCollection;
    @OneToMany(mappedBy = "cedulajugador")
    private Collection<Reservas> reservasCollection;

    public Jugadores() {
    }

    public Jugadores(String cedulajugador) {
        this.cedulajugador = cedulajugador;
    }

    public Jugadores(String cedulajugador, String nombrejugador, long celularjugador, String mailjugador) {
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

    public long getCelularjugador() {
        return celularjugador;
    }

    public void setCelularjugador(long celularjugador) {
        this.celularjugador = celularjugador;
    }

    public String getMailjugador() {
        return mailjugador;
    }

    public void setMailjugador(String mailjugador) {
        this.mailjugador = mailjugador;
    }

    public Boolean getCapitan() {
        return capitan;
    }

    public void setCapitan(Boolean capitan) {
        this.capitan = capitan;
    }

    @XmlTransient
    public Collection<Equipos> getEquiposCollection() {
        return equiposCollection;
    }

    public void setEquiposCollection(Collection<Equipos> equiposCollection) {
        this.equiposCollection = equiposCollection;
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
        return "MD.Jugadores[ cedulajugador=" + cedulajugador + " ]";
    }
    
}

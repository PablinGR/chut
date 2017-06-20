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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @author ryuku
 */
@Entity
@Table(name = "EQUIPOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipos.findAll", query = "SELECT e FROM Equipos e")
    , @NamedQuery(name = "Equipos.findByIdequipo", query = "SELECT e FROM Equipos e WHERE e.idequipo = :idequipo")
    , @NamedQuery(name = "Equipos.findByDescripcionequipo", query = "SELECT e FROM Equipos e WHERE e.descripcionequipo = :descripcionequipo")})
public class Equipos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDEQUIPO")
    private Integer idequipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCIONEQUIPO")
    private String descripcionequipo;
    @JoinTable(name = "JUGADORXEQUIPO", joinColumns = {
        @JoinColumn(name = "IDEQUIPO", referencedColumnName = "IDEQUIPO")}, inverseJoinColumns = {
        @JoinColumn(name = "CEDULAJUGADOR", referencedColumnName = "CEDULAJUGADOR")})
    @ManyToMany
    private Collection<Jugadores> jugadoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idequipo")
    private Collection<Partidos> partidosCollection;

    public Equipos() {
    }

    public Equipos(Integer idequipo) {
        this.idequipo = idequipo;
    }

    public Equipos(Integer idequipo, String descripcionequipo) {
        this.idequipo = idequipo;
        this.descripcionequipo = descripcionequipo;
    }

    public Integer getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Integer idequipo) {
        this.idequipo = idequipo;
    }

    public String getDescripcionequipo() {
        return descripcionequipo;
    }

    public void setDescripcionequipo(String descripcionequipo) {
        this.descripcionequipo = descripcionequipo;
    }

    @XmlTransient
    public Collection<Jugadores> getJugadoresCollection() {
        return jugadoresCollection;
    }

    public void setJugadoresCollection(Collection<Jugadores> jugadoresCollection) {
        this.jugadoresCollection = jugadoresCollection;
    }

    @XmlTransient
    public Collection<Partidos> getPartidosCollection() {
        return partidosCollection;
    }

    public void setPartidosCollection(Collection<Partidos> partidosCollection) {
        this.partidosCollection = partidosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idequipo != null ? idequipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipos)) {
            return false;
        }
        Equipos other = (Equipos) object;
        if ((this.idequipo == null && other.idequipo != null) || (this.idequipo != null && !this.idequipo.equals(other.idequipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Equipos[ idequipo=" + idequipo + " ]";
    }
    
}

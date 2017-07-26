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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "RESERVAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservas.findAll", query = "SELECT r FROM Reservas r")
    , @NamedQuery(name = "Reservas.findByIdreserva", query = "SELECT r FROM Reservas r WHERE r.idreserva = :idreserva")})
public class Reservas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRESERVA")
    private Integer idreserva;
    @OneToMany(mappedBy = "idreserva")
    private Collection<Campeonatos> campeonatosCollection;
    @JoinColumn(name = "CEDULAJUGADOR", referencedColumnName = "CEDULAJUGADOR")
    @ManyToOne
    private Jugadores cedulajugador;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne(optional = false)
    private Partidos idpartido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idreserva")
    private Collection<Horarios> horariosCollection;

    public Reservas() {
    }

    public Reservas(Integer idreserva) {
        this.idreserva = idreserva;
    }

    public Integer getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(Integer idreserva) {
        this.idreserva = idreserva;
    }

    @XmlTransient
    public Collection<Campeonatos> getCampeonatosCollection() {
        return campeonatosCollection;
    }

    public void setCampeonatosCollection(Collection<Campeonatos> campeonatosCollection) {
        this.campeonatosCollection = campeonatosCollection;
    }

    public Jugadores getCedulajugador() {
        return cedulajugador;
    }

    public void setCedulajugador(Jugadores cedulajugador) {
        this.cedulajugador = cedulajugador;
    }

    public Partidos getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Partidos idpartido) {
        this.idpartido = idpartido;
    }

    @XmlTransient
    public Collection<Horarios> getHorariosCollection() {
        return horariosCollection;
    }

    public void setHorariosCollection(Collection<Horarios> horariosCollection) {
        this.horariosCollection = horariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreserva != null ? idreserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservas)) {
            return false;
        }
        Reservas other = (Reservas) object;
        if ((this.idreserva == null && other.idreserva != null) || (this.idreserva != null && !this.idreserva.equals(other.idreserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Reservas[ idreserva=" + idreserva + " ]";
    }
    
}

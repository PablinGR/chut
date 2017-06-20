/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ryuku
 */
@Entity
@Table(name = "RESERVAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservas.findAll", query = "SELECT r FROM Reservas r")
    , @NamedQuery(name = "Reservas.findByIdreserva", query = "SELECT r FROM Reservas r WHERE r.idreserva = :idreserva")
    , @NamedQuery(name = "Reservas.findByCapitanreserva", query = "SELECT r FROM Reservas r WHERE r.capitanreserva = :capitanreserva")})
public class Reservas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRESERVA")
    private Integer idreserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CAPITANRESERVA")
    private String capitanreserva;
    @OneToMany(mappedBy = "idreserva")
    private Collection<Partidos> partidosCollection;
    @JoinColumn(name = "IDCAMPEONATO", referencedColumnName = "IDCAMPEONATO")
    @ManyToOne(optional = false)
    private Campeonatos idcampeonato;
    @JoinColumn(name = "IDHORARIO", referencedColumnName = "IDHORARIO")
    @ManyToOne(optional = false)
    private Horarios idhorario;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne(optional = false)
    private Partidos idpartido;

    public Reservas() {
    }

    public Reservas(Integer idreserva) {
        this.idreserva = idreserva;
    }

    public Reservas(Integer idreserva, String capitanreserva) {
        this.idreserva = idreserva;
        this.capitanreserva = capitanreserva;
    }

    public Integer getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(Integer idreserva) {
        this.idreserva = idreserva;
    }

    public String getCapitanreserva() {
        return capitanreserva;
    }

    public void setCapitanreserva(String capitanreserva) {
        this.capitanreserva = capitanreserva;
    }

    @XmlTransient
    public Collection<Partidos> getPartidosCollection() {
        return partidosCollection;
    }

    public void setPartidosCollection(Collection<Partidos> partidosCollection) {
        this.partidosCollection = partidosCollection;
    }

    public Campeonatos getIdcampeonato() {
        return idcampeonato;
    }

    public void setIdcampeonato(Campeonatos idcampeonato) {
        this.idcampeonato = idcampeonato;
    }

    public Horarios getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(Horarios idhorario) {
        this.idhorario = idhorario;
    }

    public Partidos getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Partidos idpartido) {
        this.idpartido = idpartido;
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

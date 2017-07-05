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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ryuku
 */
@Entity
@Table(name = "CAMPEONATOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campeonatos.findAll", query = "SELECT c FROM Campeonatos c")
    , @NamedQuery(name = "Campeonatos.findByIdcampeonato", query = "SELECT c FROM Campeonatos c WHERE c.idcampeonato = :idcampeonato")
    , @NamedQuery(name = "Campeonatos.findByDescripcioncampeonato", query = "SELECT c FROM Campeonatos c WHERE c.descripcioncampeonato = :descripcioncampeonato")
    , @NamedQuery(name = "Campeonatos.findByMailcampeonato", query = "SELECT c FROM Campeonatos c WHERE c.mailcampeonato = :mailcampeonato")
    , @NamedQuery(name = "Campeonatos.findByCelularcampeonato", query = "SELECT c FROM Campeonatos c WHERE c.celularcampeonato = :celularcampeonato")
    , @NamedQuery(name = "Campeonatos.findByPremiocampeonato", query = "SELECT c FROM Campeonatos c WHERE c.premiocampeonato = :premiocampeonato")})
public class Campeonatos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCAMPEONATO")
    private Integer idcampeonato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCIONCAMPEONATO")
    private String descripcioncampeonato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MAILCAMPEONATO")
    private String mailcampeonato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CELULARCAMPEONATO")
    private long celularcampeonato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREMIOCAMPEONATO")
    private float premiocampeonato;
    @JoinColumn(name = "RUCPATROCINADOR", referencedColumnName = "RUCPATROCINADOR")
    @ManyToOne(optional = false)
    private Patrocinadores rucpatrocinador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcampeonato")
    private Collection<Reservas> reservasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcampeonato")
    private Collection<Arbitros> arbitrosCollection;

    public Campeonatos() {
    }

    public Campeonatos(Integer idcampeonato) {
        this.idcampeonato = idcampeonato;
    }

    public Campeonatos(Integer idcampeonato, String descripcioncampeonato, String mailcampeonato, long celularcampeonato, float premiocampeonato) {
        this.idcampeonato = idcampeonato;
        this.descripcioncampeonato = descripcioncampeonato;
        this.mailcampeonato = mailcampeonato;
        this.celularcampeonato = celularcampeonato;
        this.premiocampeonato = premiocampeonato;
    }

    public Integer getIdcampeonato() {
        return idcampeonato;
    }

    public void setIdcampeonato(Integer idcampeonato) {
        this.idcampeonato = idcampeonato;
    }

    public String getDescripcioncampeonato() {
        return descripcioncampeonato;
    }

    public void setDescripcioncampeonato(String descripcioncampeonato) {
        this.descripcioncampeonato = descripcioncampeonato;
    }

    public String getMailcampeonato() {
        return mailcampeonato;
    }

    public void setMailcampeonato(String mailcampeonato) {
        this.mailcampeonato = mailcampeonato;
    }

    public long getCelularcampeonato() {
        return celularcampeonato;
    }

    public void setCelularcampeonato(long celularcampeonato) {
        this.celularcampeonato = celularcampeonato;
    }

    public float getPremiocampeonato() {
        return premiocampeonato;
    }

    public void setPremiocampeonato(float premiocampeonato) {
        this.premiocampeonato = premiocampeonato;
    }

    public Patrocinadores getRucpatrocinador() {
        return rucpatrocinador;
    }

    public void setRucpatrocinador(Patrocinadores rucpatrocinador) {
        this.rucpatrocinador = rucpatrocinador;
    }

    @XmlTransient
    public Collection<Reservas> getReservasCollection() {
        return reservasCollection;
    }

    public void setReservasCollection(Collection<Reservas> reservasCollection) {
        this.reservasCollection = reservasCollection;
    }

    @XmlTransient
    public Collection<Arbitros> getArbitrosCollection() {
        return arbitrosCollection;
    }

    public void setArbitrosCollection(Collection<Arbitros> arbitrosCollection) {
        this.arbitrosCollection = arbitrosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcampeonato != null ? idcampeonato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campeonatos)) {
            return false;
        }
        Campeonatos other = (Campeonatos) object;
        if ((this.idcampeonato == null && other.idcampeonato != null) || (this.idcampeonato != null && !this.idcampeonato.equals(other.idcampeonato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Campeonatos[ idcampeonato=" + idcampeonato + " ]";
    }
    
}

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "PATROCINADORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patrocinadores.findAll", query = "SELECT p FROM Patrocinadores p")
    , @NamedQuery(name = "Patrocinadores.findByRucpatrocinador", query = "SELECT p FROM Patrocinadores p WHERE p.rucpatrocinador = :rucpatrocinador")
    , @NamedQuery(name = "Patrocinadores.findByDescripcionpatrocinador", query = "SELECT p FROM Patrocinadores p WHERE p.descripcionpatrocinador = :descripcionpatrocinador")})
public class Patrocinadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RUCPATROCINADOR")
    private String rucpatrocinador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCIONPATROCINADOR")
    private String descripcionpatrocinador;
    @JoinTable(name = "CAMPEONATOSXPATROCINADORES", joinColumns = {
        @JoinColumn(name = "RUCPATROCINADOR", referencedColumnName = "RUCPATROCINADOR")}, inverseJoinColumns = {
        @JoinColumn(name = "IDCAMPEONATO", referencedColumnName = "IDCAMPEONATO")})
    @ManyToMany
    private Collection<Campeonatos> campeonatosCollection;

    public Patrocinadores() {
    }

    public Patrocinadores(String rucpatrocinador) {
        this.rucpatrocinador = rucpatrocinador;
    }

    public Patrocinadores(String rucpatrocinador, String descripcionpatrocinador) {
        this.rucpatrocinador = rucpatrocinador;
        this.descripcionpatrocinador = descripcionpatrocinador;
    }

    public String getRucpatrocinador() {
        return rucpatrocinador;
    }

    public void setRucpatrocinador(String rucpatrocinador) {
        this.rucpatrocinador = rucpatrocinador;
    }

    public String getDescripcionpatrocinador() {
        return descripcionpatrocinador;
    }

    public void setDescripcionpatrocinador(String descripcionpatrocinador) {
        this.descripcionpatrocinador = descripcionpatrocinador;
    }

    @XmlTransient
    public Collection<Campeonatos> getCampeonatosCollection() {
        return campeonatosCollection;
    }

    public void setCampeonatosCollection(Collection<Campeonatos> campeonatosCollection) {
        this.campeonatosCollection = campeonatosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rucpatrocinador != null ? rucpatrocinador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patrocinadores)) {
            return false;
        }
        Patrocinadores other = (Patrocinadores) object;
        if ((this.rucpatrocinador == null && other.rucpatrocinador != null) || (this.rucpatrocinador != null && !this.rucpatrocinador.equals(other.rucpatrocinador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Patrocinadores[ rucpatrocinador=" + rucpatrocinador + " ]";
    }
    
}

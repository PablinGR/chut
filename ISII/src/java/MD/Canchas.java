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
@Table(name = "CANCHAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canchas.findAll", query = "SELECT c FROM Canchas c")
    , @NamedQuery(name = "Canchas.findByIdcancha", query = "SELECT c FROM Canchas c WHERE c.idcancha = :idcancha")
    , @NamedQuery(name = "Canchas.findByDescripcioncancha", query = "SELECT c FROM Canchas c WHERE c.descripcioncancha = :descripcioncancha")
    , @NamedQuery(name = "Canchas.findBySectorcancha", query = "SELECT c FROM Canchas c WHERE c.sectorcancha = :sectorcancha")})
public class Canchas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCANCHA")
    private Integer idcancha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCIONCANCHA")
    private String descripcioncancha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SECTORCANCHA")
    private String sectorcancha;
    @JoinTable(name = "CANCHASXPROPIETARIO", joinColumns = {
        @JoinColumn(name = "IDCANCHA", referencedColumnName = "IDCANCHA")}, inverseJoinColumns = {
        @JoinColumn(name = "CEDULAPROPIETARIOS", referencedColumnName = "CEDULAPROPIETARIOS")})
    @ManyToMany
    private Collection<Propietarios> propietariosCollection;
    @OneToMany(mappedBy = "idcancha")
    private Collection<Partidos> partidosCollection;

    public Canchas() {
    }

    public Canchas(Integer idcancha) {
        this.idcancha = idcancha;
    }

    public Canchas(Integer idcancha, String descripcioncancha, String sectorcancha) {
        this.idcancha = idcancha;
        this.descripcioncancha = descripcioncancha;
        this.sectorcancha = sectorcancha;
    }

    public Integer getIdcancha() {
        return idcancha;
    }

    public void setIdcancha(Integer idcancha) {
        this.idcancha = idcancha;
    }

    public String getDescripcioncancha() {
        return descripcioncancha;
    }

    public void setDescripcioncancha(String descripcioncancha) {
        this.descripcioncancha = descripcioncancha;
    }

    public String getSectorcancha() {
        return sectorcancha;
    }

    public void setSectorcancha(String sectorcancha) {
        this.sectorcancha = sectorcancha;
    }

    @XmlTransient
    public Collection<Propietarios> getPropietariosCollection() {
        return propietariosCollection;
    }

    public void setPropietariosCollection(Collection<Propietarios> propietariosCollection) {
        this.propietariosCollection = propietariosCollection;
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
        hash += (idcancha != null ? idcancha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canchas)) {
            return false;
        }
        Canchas other = (Canchas) object;
        if ((this.idcancha == null && other.idcancha != null) || (this.idcancha != null && !this.idcancha.equals(other.idcancha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Canchas[ idcancha=" + idcancha + " ]";
    }
    
}

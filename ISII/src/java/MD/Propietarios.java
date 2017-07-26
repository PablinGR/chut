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
 * @author Diego
 */
@Entity
@Table(name = "PROPIETARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietarios.findAll", query = "SELECT p FROM Propietarios p")
    , @NamedQuery(name = "Propietarios.findByCedulapropietarios", query = "SELECT p FROM Propietarios p WHERE p.cedulapropietarios = :cedulapropietarios")
    , @NamedQuery(name = "Propietarios.findByNombrepropietarios", query = "SELECT p FROM Propietarios p WHERE p.nombrepropietarios = :nombrepropietarios")
    , @NamedQuery(name = "Propietarios.findByCelularpropietarios", query = "SELECT p FROM Propietarios p WHERE p.celularpropietarios = :celularpropietarios")
    , @NamedQuery(name = "Propietarios.findByMailpropietarios", query = "SELECT p FROM Propietarios p WHERE p.mailpropietarios = :mailpropietarios")})
public class Propietarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "CEDULAPROPIETARIOS")
    private String cedulapropietarios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBREPROPIETARIOS")
    private String nombrepropietarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CELULARPROPIETARIOS")
    private long celularpropietarios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MAILPROPIETARIOS")
    private String mailpropietarios;
    @ManyToMany(mappedBy = "propietariosCollection")
    private Collection<Canchas> canchasCollection;

    public Propietarios() {
    }

    public Propietarios(String cedulapropietarios) {
        this.cedulapropietarios = cedulapropietarios;
    }

    public Propietarios(String cedulapropietarios, String nombrepropietarios, long celularpropietarios, String mailpropietarios) {
        this.cedulapropietarios = cedulapropietarios;
        this.nombrepropietarios = nombrepropietarios;
        this.celularpropietarios = celularpropietarios;
        this.mailpropietarios = mailpropietarios;
    }

    public String getCedulapropietarios() {
        return cedulapropietarios;
    }

    public void setCedulapropietarios(String cedulapropietarios) {
        this.cedulapropietarios = cedulapropietarios;
    }

    public String getNombrepropietarios() {
        return nombrepropietarios;
    }

    public void setNombrepropietarios(String nombrepropietarios) {
        this.nombrepropietarios = nombrepropietarios;
    }

    public long getCelularpropietarios() {
        return celularpropietarios;
    }

    public void setCelularpropietarios(long celularpropietarios) {
        this.celularpropietarios = celularpropietarios;
    }

    public String getMailpropietarios() {
        return mailpropietarios;
    }

    public void setMailpropietarios(String mailpropietarios) {
        this.mailpropietarios = mailpropietarios;
    }

    @XmlTransient
    public Collection<Canchas> getCanchasCollection() {
        return canchasCollection;
    }

    public void setCanchasCollection(Collection<Canchas> canchasCollection) {
        this.canchasCollection = canchasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedulapropietarios != null ? cedulapropietarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietarios)) {
            return false;
        }
        Propietarios other = (Propietarios) object;
        if ((this.cedulapropietarios == null && other.cedulapropietarios != null) || (this.cedulapropietarios != null && !this.cedulapropietarios.equals(other.cedulapropietarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Propietarios[ cedulapropietarios=" + cedulapropietarios + " ]";
    }
    
}

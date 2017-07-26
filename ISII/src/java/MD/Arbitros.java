/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
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
 * @author Diego
 */
@Entity
@Table(name = "ARBITROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Arbitros.findAll", query = "SELECT a FROM Arbitros a")
    , @NamedQuery(name = "Arbitros.findByCedulaarbitro", query = "SELECT a FROM Arbitros a WHERE a.cedulaarbitro = :cedulaarbitro")
    , @NamedQuery(name = "Arbitros.findByNombrearbitro", query = "SELECT a FROM Arbitros a WHERE a.nombrearbitro = :nombrearbitro")})
public class Arbitros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CEDULAARBITRO")
    private String cedulaarbitro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBREARBITRO")
    private String nombrearbitro;
    @JoinColumn(name = "IDCAMPEONATO", referencedColumnName = "IDCAMPEONATO")
    @ManyToOne(optional = false)
    private Campeonatos idcampeonato;

    public Arbitros() {
    }

    public Arbitros(String cedulaarbitro) {
        this.cedulaarbitro = cedulaarbitro;
    }

    public Arbitros(String cedulaarbitro, String nombrearbitro) {
        this.cedulaarbitro = cedulaarbitro;
        this.nombrearbitro = nombrearbitro;
    }

    public String getCedulaarbitro() {
        return cedulaarbitro;
    }

    public void setCedulaarbitro(String cedulaarbitro) {
        this.cedulaarbitro = cedulaarbitro;
    }

    public String getNombrearbitro() {
        return nombrearbitro;
    }

    public void setNombrearbitro(String nombrearbitro) {
        this.nombrearbitro = nombrearbitro;
    }

    public Campeonatos getIdcampeonato() {
        return idcampeonato;
    }

    public void setIdcampeonato(Campeonatos idcampeonato) {
        this.idcampeonato = idcampeonato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedulaarbitro != null ? cedulaarbitro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Arbitros)) {
            return false;
        }
        Arbitros other = (Arbitros) object;
        if ((this.cedulaarbitro == null && other.cedulaarbitro != null) || (this.cedulaarbitro != null && !this.cedulaarbitro.equals(other.cedulaarbitro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Arbitros[ cedulaarbitro=" + cedulaarbitro + " ]";
    }
    
}

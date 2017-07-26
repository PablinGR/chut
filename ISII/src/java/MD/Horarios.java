/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "HORARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h")
    , @NamedQuery(name = "Horarios.findByIdhorario", query = "SELECT h FROM Horarios h WHERE h.idhorario = :idhorario")
    , @NamedQuery(name = "Horarios.findByDescripcionhorario", query = "SELECT h FROM Horarios h WHERE h.descripcionhorario = :descripcionhorario")
    , @NamedQuery(name = "Horarios.findByIniciohorario", query = "SELECT h FROM Horarios h WHERE h.iniciohorario = :iniciohorario")
    , @NamedQuery(name = "Horarios.findByFinhorario", query = "SELECT h FROM Horarios h WHERE h.finhorario = :finhorario")})
public class Horarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDHORARIO")
    private Integer idhorario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCIONHORARIO")
    private String descripcionhorario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INICIOHORARIO")
    @Temporal(TemporalType.DATE)
    private Date iniciohorario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FINHORARIO")
    @Temporal(TemporalType.DATE)
    private Date finhorario;
    @JoinColumn(name = "IDRESERVA", referencedColumnName = "IDRESERVA")
    @ManyToOne(optional = false)
    private Reservas idreserva;

    public Horarios() {
    }

    public Horarios(Integer idhorario) {
        this.idhorario = idhorario;
    }

    public Horarios(Integer idhorario, String descripcionhorario, Date iniciohorario, Date finhorario) {
        this.idhorario = idhorario;
        this.descripcionhorario = descripcionhorario;
        this.iniciohorario = iniciohorario;
        this.finhorario = finhorario;
    }

    public Integer getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(Integer idhorario) {
        this.idhorario = idhorario;
    }

    public String getDescripcionhorario() {
        return descripcionhorario;
    }

    public void setDescripcionhorario(String descripcionhorario) {
        this.descripcionhorario = descripcionhorario;
    }

    public Date getIniciohorario() {
        return iniciohorario;
    }

    public void setIniciohorario(Date iniciohorario) {
        this.iniciohorario = iniciohorario;
    }

    public Date getFinhorario() {
        return finhorario;
    }

    public void setFinhorario(Date finhorario) {
        this.finhorario = finhorario;
    }

    public Reservas getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(Reservas idreserva) {
        this.idreserva = idreserva;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhorario != null ? idhorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.idhorario == null && other.idhorario != null) || (this.idhorario != null && !this.idhorario.equals(other.idhorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MD.Horarios[ idhorario=" + idhorario + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd05.mor.entities;

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


@Entity
@Table(name = "extra_service_mapping")
@NamedQueries({
    @NamedQuery(name = "ExtraServiceMapping.findAll", query = "SELECT e FROM ExtraServiceMapping e"),
    @NamedQuery(name = "ExtraServiceMapping.findById", query = "SELECT e FROM ExtraServiceMapping e WHERE e.id = :id"),
    @NamedQuery(name = "ExtraServiceMapping.findByVersion", query = "SELECT e FROM ExtraServiceMapping e WHERE e.version = :version")})
public class ExtraServiceMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version ")
    private long version;
    @JoinColumn(name = "extra_service_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExtraService extraServiceId;
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Reservation reservationId;

    public ExtraServiceMapping() {
    }

    public ExtraServiceMapping(Long id) {
        this.id = id;
    }

    public ExtraServiceMapping(Long id, long version) {
        this.id = id;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public ExtraService getExtraServiceId() {
        return extraServiceId;
    }

    public void setExtraServiceId(ExtraService extraServiceId) {
        this.extraServiceId = extraServiceId;
    }

    public Reservation getReservationId() {
        return reservationId;
    }

    public void setReservationId(Reservation reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExtraServiceMapping)) {
            return false;
        }
        ExtraServiceMapping other = (ExtraServiceMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.ExtraServiceMapping[ id=" + id + " ]";
    }
    
}

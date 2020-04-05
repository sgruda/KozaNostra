/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd05.mos.entities;

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


@Entity
@Table(name = "event_types")
@NamedQueries({
    @NamedQuery(name = "EventTypes.findAll", query = "SELECT e FROM EventTypes e"),
    @NamedQuery(name = "EventTypes.findById", query = "SELECT e FROM EventTypes e WHERE e.id = :id"),
    @NamedQuery(name = "EventTypes.findByTypeName", query = "SELECT e FROM EventTypes e WHERE e.typeName = :typeName"),
    @NamedQuery(name = "EventTypes.findByVersion", query = "SELECT e FROM EventTypes e WHERE e.version = :version")})
public class EventTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "type_name")
    private String typeName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Hall hallId;

    public EventTypes() {
    }

    public EventTypes(Long id) {
        this.id = id;
    }

    public EventTypes(Long id, String typeName, long version) {
        this.id = id;
        this.typeName = typeName;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Hall getHallId() {
        return hallId;
    }

    public void setHallId(Hall hallId) {
        this.hallId = hallId;
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
        if (!(object instanceof EventTypes)) {
            return false;
        }
        EventTypes other = (EventTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mos.entities.EventTypes[ id=" + id + " ]";
    }
    
}

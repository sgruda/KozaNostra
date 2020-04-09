package pl.lodz.p.it.ssbd2020.ssbd05.entities.mos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "event_types", schema = "ssbd05schema", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"type_name"})
})
@TableGenerator(name = "EventTypesIdGen", table = "id_generator", schema = "ssbd05schema", pkColumnName = "class_name", pkColumnValue = "event_types", valueColumnName = "id_range")
@NamedQueries({
    @NamedQuery(name = "EventTypes.findAll", query = "SELECT e FROM EventTypes e"),
    @NamedQuery(name = "EventTypes.findById", query = "SELECT e FROM EventTypes e WHERE e.id = :id"),
    @NamedQuery(name = "EventTypes.findByTypeName", query = "SELECT e FROM EventTypes e WHERE e.typeName = :typeName"),
    @NamedQuery(name = "EventTypes.findByVersion", query = "SELECT e FROM EventTypes e WHERE e.version = :version")})
public class EventTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "EventTypesIdGen")
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "type_name", nullable = false, length = 32, unique = true)
    private String typeName;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
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
        return "pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventTypes[ id=" + id + " ]";
    }
    
}

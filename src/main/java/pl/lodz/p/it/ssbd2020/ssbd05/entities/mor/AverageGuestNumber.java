package pl.lodz.p.it.ssbd2020.ssbd05.entities.mor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "average_guest_number", schema = "ssbd05schema")
@TableGenerator(name = "AverageGuestNumberIdGen", table = "id_generator", schema = "ssbd05schema", pkColumnName = "class_name", pkColumnValue = "average_guest_number", valueColumnName = "id_range")
@NamedQueries({
        @NamedQuery(name = "AverageGuestNumber.findAll", query = "SELECT a FROM AverageGuestNumber a"),
        @NamedQuery(name = "AverageGuestNumber.findById", query = "SELECT a FROM AverageGuestNumber a WHERE a.id = :id"),
        @NamedQuery(name = "AverageGuestNumber.findByGuestSum", query = "SELECT a FROM AverageGuestNumber a WHERE a.guestSum = :guestSum"),
        @NamedQuery(name = "AverageGuestNumber.findByEventSum", query = "SELECT a FROM AverageGuestNumber a WHERE a.eventSum = :eventSum"),
        @NamedQuery(name = "AverageGuestNumber.findByAverage", query = "SELECT a FROM AverageGuestNumber a WHERE a.average = :average")})
public class AverageGuestNumber {

    private static final long serialVersionUID = 1L;

    @Setter(lombok.AccessLevel.NONE)
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AverageGuestNumberIdGen")
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "guest_sum", nullable = false)
    private Long guestSum;

    @Basic(optional = false)
    @NotNull
    @Column(name = "event_sum", nullable = false)
    private Long eventSum;

    @Basic(optional = false)
    @NotNull
    @Column(name = "average", nullable = false)
    private Long average;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;

    public AverageGuestNumber() {

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AverageGuestNumber)) {
            return false;
        }
        AverageGuestNumber other = (AverageGuestNumber) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.AverageGuestNumber[ id=" + id + " version=" + version + " ]";
    }
}

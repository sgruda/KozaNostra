package pl.lodz.p.it.ssbd2020.ssbd05.entities.mor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "extra_service", schema = "ssbd05schema", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"service_name"})
})
@TableGenerator(name = "ExtraServiceIdGen", table = "id_generator", schema = "ssbd05schema", pkColumnName = "class_name", pkColumnValue = "extra_service", valueColumnName = "id_range")
@NamedQueries({
        @NamedQuery(name = "ExtraService.findAll", query = "SELECT e FROM ExtraService e"),
        @NamedQuery(name = "ExtraService.findById", query = "SELECT e FROM ExtraService e WHERE e.id = :id"),
        @NamedQuery(name = "ExtraService.findByDescription", query = "SELECT e FROM ExtraService e WHERE e.description = :description"),
        @NamedQuery(name = "ExtraService.findByPrice", query = "SELECT e FROM ExtraService e WHERE e.price = :price"),
        @NamedQuery(name = "ExtraService.findByServiceName", query = "SELECT e FROM ExtraService e WHERE e.serviceName = :serviceName"),
        @NamedQuery(name = "ExtraService.findByActive", query = "SELECT e FROM ExtraService e WHERE e.active = :active")})
public class ExtraService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(lombok.AccessLevel.NONE)
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ExtraServiceIdGen")
    @NotNull(message = "{validation.notnull}")
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Size(min = 1, max = 512, message = "{validation.size}")
    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @Digits(integer = 7, fraction = 2, message = "{validation.digits}")
    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Column(name = "price", nullable = false)
    private double price;

    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Size(min = 1, max = 32, message = "{validation.size}")
    @Column(name = "service_name", nullable = false, length = 32, unique = true)
    private String serviceName;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull(message = "{validation.notnull}")
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;

    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private boolean active;

    public ExtraService() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExtraService)) {
            return false;
        }
        ExtraService other = (ExtraService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService[ id=" + id + " version=" + version + " ]";
    }

}

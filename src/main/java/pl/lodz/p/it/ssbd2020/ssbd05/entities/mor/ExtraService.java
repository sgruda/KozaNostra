package pl.lodz.p.it.ssbd2020.ssbd05.entities.mor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "price", nullable = false)
    private double price;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "service_name", nullable = false, length = 32, unique = true)
    private String serviceName;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;

    @Basic(optional = false)
    @NotNull
    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private boolean active;

    public ExtraService() {
    }

    public ExtraService(Long id) {
        this.id = id;
    }

    public ExtraService(Long id, String description, double price, String serviceName, boolean active) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.serviceName = serviceName;
        this.active = active;
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
        return "pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService[ id=" + id + " ]";
    }

}

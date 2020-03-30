package pl.lodz.p.it.ssbd2020.ssbd05.mok.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "access_level_mapping", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "access_level"})})
@NamedQueries({
        @NamedQuery(name = "AccessLevelMapping.findAll", query = "SELECT a FROM AccessLevelMapping a"),
        @NamedQuery(name = "AccessLevelMapping.findById", query = "SELECT a FROM AccessLevelMapping a WHERE a.id = :id"),
        @NamedQuery(name = "AccessLevelMapping.findByAccessLevel", query = "SELECT a FROM AccessLevelMapping a WHERE a.accessLevel = :accessLevel"),
        @NamedQuery(name = "AccessLevelMapping.findByVersion", query = "SELECT a FROM AccessLevelMapping a WHERE a.version = :version")})
public class AccessLevelMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "access_level", nullable = false, length = 32)
    private String accessLevel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "version", nullable = false)
    private long version;

    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Account accountId;

    public AccessLevelMapping() {

    }

    public AccessLevelMapping(Long id) {
        this.id = id;
    }

    public AccessLevelMapping(Long id, String accessLevel, long version) {
        this.id = id;
        this.accessLevel = accessLevel;
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
        if (!(object instanceof AccessLevelMapping)) {
            return false;
        }
        AccessLevelMapping other = (AccessLevelMapping) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.AccessLevelMapping[ id=" + id + " ]";
    }
}

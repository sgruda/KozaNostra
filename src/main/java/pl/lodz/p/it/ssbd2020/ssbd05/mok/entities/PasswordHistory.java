package pl.lodz.p.it.ssbd2020.ssbd05.mok.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "password_history", schema = "ssbd05schema", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"password"})
})
@NamedQueries({
    @NamedQuery(name = "PasswordHistory.findAll", query = "SELECT p FROM PasswordHistory p"),
    @NamedQuery(name = "PasswordHistory.findById", query = "SELECT p FROM PasswordHistory p WHERE p.id = :id"),
    @NamedQuery(name = "PasswordHistory.findByPassword", query = "SELECT p FROM PasswordHistory p WHERE p.password = :password"),
    @NamedQuery(name = "PasswordHistory.findByVersion", query = "SELECT p FROM PasswordHistory p WHERE p.version = :version")})
public class PasswordHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password", nullable = false, length = 64, unique = true)
    private String password;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;

    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account accountId;

    public PasswordHistory() {
    }

    public PasswordHistory(Long id) {
        this.id = id;
    }

    public PasswordHistory(Long id, String password, long version) {
        this.id = id;
        this.password = password;
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
        if (!(object instanceof PasswordHistory)) {
            return false;
        }
        PasswordHistory other = (PasswordHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.PasswordHistory[ id=" + id + " ]";
    }
    
}

package pl.lodz.p.it.ssbd2020.ssbd05.entities.mok;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "forgot_password_token", schema = "ssbd05schema")
@TableGenerator(name = "ForgotPasswordTokenIdGen", table = "id_generator", schema = "ssbd05schema", pkColumnName = "class_name", valueColumnName = "id_range", pkColumnValue = "forgot_password_token")
@NamedQueries({
        @NamedQuery(name = "ForgotPasswordToken.findAll", query = "SELECT f FROM ForgotPasswordToken f"),
        @NamedQuery(name = "ForgotPasswordToken.findById", query = "SELECT f FROM ForgotPasswordToken f WHERE f.id = :id"),
        @NamedQuery(name = "ForgotPasswordToken.findByExpireDate", query = "SELECT f FROM ForgotPasswordToken f WHERE f.expireDate = :expireDate"),
        @NamedQuery(name = "ForgotPasswordToken.findByHash", query = "SELECT f FROM ForgotPasswordToken f WHERE f.hash = :hash")})
public class ForgotPasswordToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ForgotPasswordTokenIdGen")
    @Column(name = "id", nullable = false)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    @Future(message = "{validation.date.future}")
    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Column(name = "expire_date", nullable = false, updatable = false)
    private LocalDateTime expireDate;

    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Size(min = 64, max = 64, message = "{validation.size}")
    @Column(name = "hash", nullable = false, unique = true, updatable = false)
    private String hash;

    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    @OneToOne(optional = false)
    private Account account;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull(message = "{validation.notnull}")
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;


    public ForgotPasswordToken() {
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ForgotPasswordToken)) {
            return false;
        }
        ForgotPasswordToken other = (ForgotPasswordToken) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.ForgotPasswordToken[ id=" + id + " version=" + version + " ]";
    }

}

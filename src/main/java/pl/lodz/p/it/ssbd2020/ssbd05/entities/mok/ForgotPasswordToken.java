package pl.lodz.p.it.ssbd2020.ssbd05.entities.mok;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


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
    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ForgotPasswordTokenIdGen")
    @Column(name = "id", nullable = false)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "expire_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "hash", nullable = false, unique = true)
    private String hash;

    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    @OneToOne(optional = false)
    private Account account;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;



    public ForgotPasswordToken() {
    }

    public ForgotPasswordToken(Long id) {
        this.id = id;
    }

    public ForgotPasswordToken(Long id, Date expireDate, String hash,Long version) {
        this.id = id;
        this.expireDate = expireDate;
        this.hash = hash;
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
        return "pl.lodz.p.it.ssbd2020.ssbd05.ForgotPasswordToken[ id=" + id + " ]";
    }

}

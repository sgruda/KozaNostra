package pl.lodz.p.it.ssbd2020.ssbd05.entities.mok;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "forgot_password_token", schema = "ssbd05schema")
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
    @Column(name = "id")
    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "hash")
    private String hash;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
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

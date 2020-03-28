/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd05.mok.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "account_personal_data", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
@NamedQueries({
    @NamedQuery(name = "AccountPersonalData.findAll", query = "SELECT a FROM AccountPersonalData a"),
    @NamedQuery(name = "AccountPersonalData.findById", query = "SELECT a FROM AccountPersonalData a WHERE a.id = :id"),
    @NamedQuery(name = "AccountPersonalData.findByFirstname", query = "SELECT a FROM AccountPersonalData a WHERE a.firstname = :firstname"),
    @NamedQuery(name = "AccountPersonalData.findByLastname", query = "SELECT a FROM AccountPersonalData a WHERE a.lastname = :lastname"),
    @NamedQuery(name = "AccountPersonalData.findByEmail", query = "SELECT a FROM AccountPersonalData a WHERE a.email = :email"),
    @NamedQuery(name = "AccountPersonalData.findByVersion", query = "SELECT a FROM AccountPersonalData a WHERE a.version = :version")})
public class AccountPersonalData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "firstname", nullable = false, length = 2147483647)
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "lastname", nullable = false, length = 2147483647)
    private String lastname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "email", nullable = false, length = 2147483647)
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version", nullable = false)
    private long version;
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private AccountLoginData accountLoginData;

    public AccountPersonalData() {
    }

    public AccountPersonalData(Long id) {
        this.id = id;
    }

    public AccountPersonalData(Long id, String firstname, String lastname, String email, long version) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public AccountLoginData getAccountLoginData() {
        return accountLoginData;
    }

    public void setAccountLoginData(AccountLoginData accountLoginData) {
        this.accountLoginData = accountLoginData;
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
        if (!(object instanceof AccountPersonalData)) {
            return false;
        }
        AccountPersonalData other = (AccountPersonalData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.AccountPersonalData[ id=" + id + " ]";
    }
    
}

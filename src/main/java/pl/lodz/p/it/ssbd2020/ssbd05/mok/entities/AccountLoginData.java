/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd05.mok.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "account_login_data", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})})
@NamedQueries({
    @NamedQuery(name = "AccountLoginData.findAll", query = "SELECT a FROM AccountLoginData a"),
    @NamedQuery(name = "AccountLoginData.findById", query = "SELECT a FROM AccountLoginData a WHERE a.id = :id"),
    @NamedQuery(name = "AccountLoginData.findByLogin", query = "SELECT a FROM AccountLoginData a WHERE a.login = :login"),
    @NamedQuery(name = "AccountLoginData.findByPassword", query = "SELECT a FROM AccountLoginData a WHERE a.password = :password"),
    @NamedQuery(name = "AccountLoginData.findByActive", query = "SELECT a FROM AccountLoginData a WHERE a.active = :active"),
    @NamedQuery(name = "AccountLoginData.findByConfirmed", query = "SELECT a FROM AccountLoginData a WHERE a.confirmed = :confirmed"),
    @NamedQuery(name = "AccountLoginData.findByVersion", query = "SELECT a FROM AccountLoginData a WHERE a.version = :version")})
public class AccountLoginData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "login", nullable = false, length = 2147483647)
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "password", nullable = false, length = 2147483647)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "confirmed", nullable = false)
    private boolean confirmed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version", nullable = false)
    private long version;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accountLoginData")
    private AccountPersonalData accountPersonalData;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accountLoginData")
    private AuthenticationData authenticationData;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<AccessLevelMapping> accessLevelMappingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<PasswordHistory> passwordHistoryCollection;

    public AccountLoginData() {
    }

    public AccountLoginData(Long id) {
        this.id = id;
    }

    public AccountLoginData(Long id, String login, String password, boolean active, boolean confirmed, long version) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.active = active;
        this.confirmed = confirmed;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public AccountPersonalData getAccountPersonalData() {
        return accountPersonalData;
    }

    public void setAccountPersonalData(AccountPersonalData accountPersonalData) {
        this.accountPersonalData = accountPersonalData;
    }

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public Collection<AccessLevelMapping> getAccessLevelMappingCollection() {
        return accessLevelMappingCollection;
    }

    public void setAccessLevelMappingCollection(Collection<AccessLevelMapping> accessLevelMappingCollection) {
        this.accessLevelMappingCollection = accessLevelMappingCollection;
    }

    public Collection<PasswordHistory> getPasswordHistoryCollection() {
        return passwordHistoryCollection;
    }

    public void setPasswordHistoryCollection(Collection<PasswordHistory> passwordHistoryCollection) {
        this.passwordHistoryCollection = passwordHistoryCollection;
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
        if (!(object instanceof AccountLoginData)) {
            return false;
        }
        AccountLoginData other = (AccountLoginData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.AccountLoginData[ id=" + id + " ]";
    }
    
}

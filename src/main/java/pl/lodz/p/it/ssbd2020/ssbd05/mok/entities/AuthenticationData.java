/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd05.mok.entities;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "authentication_data",  schema = "ssbd05schema")
@NamedQueries({
    @NamedQuery(name = "AuthenticationData.findAll", query = "SELECT a FROM AuthenticationData a"),
    @NamedQuery(name = "AuthenticationData.findById", query = "SELECT a FROM AuthenticationData a WHERE a.id = :id"),
    @NamedQuery(name = "AuthenticationData.findByLastSuccessfulAuth", query = "SELECT a FROM AuthenticationData a WHERE a.lastSuccessfulAuth = :lastSuccessfulAuth"),
    @NamedQuery(name = "AuthenticationData.findByLastFailedAuth", query = "SELECT a FROM AuthenticationData a WHERE a.lastFailedAuth = :lastFailedAuth"),
    @NamedQuery(name = "AuthenticationData.findByLastAuthIp", query = "SELECT a FROM AuthenticationData a WHERE a.lastAuthIp = :lastAuthIp"),
    @NamedQuery(name = "AuthenticationData.findByFailedAuthCounter", query = "SELECT a FROM AuthenticationData a WHERE a.failedAuthCounter = :failedAuthCounter"),
    @NamedQuery(name = "AuthenticationData.findByForcePasswordChange", query = "SELECT a FROM AuthenticationData a WHERE a.forcePasswordChange = :forcePasswordChange")})
public class AuthenticationData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "last_successful_auth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSuccessfulAuth;
    @Column(name = "last_failed_auth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastFailedAuth;
    @Size(max = 255)
    @Column(name = "last_auth_ip")
    private String lastAuthIp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "failed_auth_counter")
    private int failedAuthCounter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "force_password_change")
    private boolean forcePasswordChange;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private AccountLoginData accountLoginData;

    public AuthenticationData() {
    }

    public AuthenticationData(Long id) {
        this.id = id;
    }

    public AuthenticationData(Long id, int failedAuthCounter, boolean forcePasswordChange) {
        this.id = id;
        this.failedAuthCounter = failedAuthCounter;
        this.forcePasswordChange = forcePasswordChange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastSuccessfulAuth() {
        return lastSuccessfulAuth;
    }

    public void setLastSuccessfulAuth(Date lastSuccessfulAuth) {
        this.lastSuccessfulAuth = lastSuccessfulAuth;
    }

    public Date getLastFailedAuth() {
        return lastFailedAuth;
    }

    public void setLastFailedAuth(Date lastFailedAuth) {
        this.lastFailedAuth = lastFailedAuth;
    }

    public String getLastAuthIp() {
        return lastAuthIp;
    }

    public void setLastAuthIp(String lastAuthIp) {
        this.lastAuthIp = lastAuthIp;
    }

    public int getFailedAuthCounter() {
        return failedAuthCounter;
    }

    public void setFailedAuthCounter(int failedAuthCounter) {
        this.failedAuthCounter = failedAuthCounter;
    }

    public boolean getForcePasswordChange() {
        return forcePasswordChange;
    }

    public void setForcePasswordChange(boolean forcePasswordChange) {
        this.forcePasswordChange = forcePasswordChange;
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
        if (!(object instanceof AuthenticationData)) {
            return false;
        }
        AuthenticationData other = (AuthenticationData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.AuthenticationData[ id=" + id + " ]";
    }
    
}

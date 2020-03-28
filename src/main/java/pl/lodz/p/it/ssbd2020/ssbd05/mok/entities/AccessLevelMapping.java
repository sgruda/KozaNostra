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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
    @Size(min = 1, max = 2147483647)
    @Column(name = "access_level", nullable = false, length = 2147483647)
    private String accessLevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version", nullable = false)
    private long version;
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private AccountLoginData accountId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public AccountLoginData getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountLoginData accountId) {
        this.accountId = accountId;
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
        if (!(object instanceof AccessLevelMapping)) {
            return false;
        }
        AccessLevelMapping other = (AccessLevelMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.AccessLevelMapping[ id=" + id + " ]";
    }
    
}

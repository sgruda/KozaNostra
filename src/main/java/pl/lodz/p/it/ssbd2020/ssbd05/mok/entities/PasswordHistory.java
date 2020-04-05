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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "password_history", schema = "ssbd05schema")
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
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
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

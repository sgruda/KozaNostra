/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd05.mos.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "hall")
@NamedQueries({
    @NamedQuery(name = "Hall.findAll", query = "SELECT h FROM Hall h"),
    @NamedQuery(name = "Hall.findById", query = "SELECT h FROM Hall h WHERE h.id = :id"),
    @NamedQuery(name = "Hall.findByName", query = "SELECT h FROM Hall h WHERE h.name = :name"),
    @NamedQuery(name = "Hall.findByCapacity", query = "SELECT h FROM Hall h WHERE h.capacity = :capacity"),
    @NamedQuery(name = "Hall.findByActive", query = "SELECT h FROM Hall h WHERE h.active = :active"),
    @NamedQuery(name = "Hall.findByArea", query = "SELECT h FROM Hall h WHERE h.area = :area"),
    @NamedQuery(name = "Hall.findByDescription", query = "SELECT h FROM Hall h WHERE h.description = :description"),
    @NamedQuery(name = "Hall.findByPrice", query = "SELECT h FROM Hall h WHERE h.price = :price"),
    @NamedQuery(name = "Hall.findByVersion", query = "SELECT h FROM Hall h WHERE h.version = :version")})
public class Hall implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacity")
    private int capacity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "area")
    private double area;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallId")
    private Collection<EventTypes> eventTypesCollection;
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address addressId;

    public Hall() {
    }

    public Hall(Long id) {
        this.id = id;
    }

    public Hall(Long id, String name, int capacity, boolean active, double area, String description, double price, long version) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.active = active;
        this.area = area;
        this.description = description;
        this.price = price;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Collection<EventTypes> getEventTypesCollection() {
        return eventTypesCollection;
    }

    public void setEventTypesCollection(Collection<EventTypes> eventTypesCollection) {
        this.eventTypesCollection = eventTypesCollection;
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
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
        if (!(object instanceof Hall)) {
            return false;
        }
        Hall other = (Hall) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.mos.entities.Hall[ id=" + id + " ]";
    }
    
}

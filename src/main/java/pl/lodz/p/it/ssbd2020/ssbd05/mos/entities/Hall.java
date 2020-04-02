package pl.lodz.p.it.ssbd2020.ssbd05.mos.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "hall")
@NamedQueries({
    @NamedQuery(name = "Hall.findAll", query = "SELECT h FROM Hall h"),
    @NamedQuery(name = "Hall.findById", query = "SELECT h FROM Hall h WHERE h.id = :id"),
    @NamedQuery(name = "Hall.findByName", query = "SELECT h FROM Hall h WHERE h.name = :name"),
    @NamedQuery(name = "Hall.findByCapacity", query = "SELECT h FROM Hall h WHERE h.capacity = :capacity"),
    @NamedQuery(name = "Hall.findByAddress", query = "SELECT h FROM Hall h WHERE h.address = :address"),
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
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "address", length = 32, nullable = false)
    private String address;

    @Basic(optional = false)
    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active;

    @Basic(optional = false)
    @NotNull
    @Column(name = "area", nullable = false)
    private double area;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "description", nullable = false)
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "price", nullable = false)
    private double price;

    @Basic(optional = false)
    @NotNull
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeId")
    private List<EventTypes> eventTypesCollection = new ArrayList<>();

    public Hall() {
    }

    public Hall(Long id) {
        this.id = id;
    }

    public Hall(Long id, String name, int capacity, String address, boolean active, double area, String description, double price, long version) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.address = address;
        this.active = active;
        this.area = area;
        this.description = description;
        this.price = price;
        this.version = version;
    }

    public List<EventTypes> getEventTypesCollection() {
        return eventTypesCollection;
    }

    public void setEventTypesCollection(List<EventTypes> eventTypesCollection) {
        this.eventTypesCollection = eventTypesCollection;
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

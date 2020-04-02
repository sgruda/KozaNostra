/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd05.mor.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "extra_service")
@NamedQueries({
    @NamedQuery(name = "ExtraService.findAll", query = "SELECT e FROM ExtraService e"),
    @NamedQuery(name = "ExtraService.findById", query = "SELECT e FROM ExtraService e WHERE e.id = :id"),
    @NamedQuery(name = "ExtraService.findByDescription", query = "SELECT e FROM ExtraService e WHERE e.description = :description"),
    @NamedQuery(name = "ExtraService.findByPrice", query = "SELECT e FROM ExtraService e WHERE e.price = :price"),
    @NamedQuery(name = "ExtraService.findByServiceName", query = "SELECT e FROM ExtraService e WHERE e.serviceName = :serviceName"),
    @NamedQuery(name = "ExtraService.findByVersion", query = "SELECT e FROM ExtraService e WHERE e.version = :version")})
public class ExtraService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "service_name")
    private String serviceName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "extraServiceId")
    private List<ExtraServiceMapping> extraServiceMappingCollection = new ArrayList<>();

    public ExtraService() {
    }

    public ExtraService(Long id) {
        this.id = id;
    }

    public ExtraService(Long id, String description, double price, String serviceName, long version) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.serviceName = serviceName;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<ExtraServiceMapping> getExtraServiceMappingCollection() {
        return extraServiceMappingCollection;
    }

    public void setExtraServiceMappingCollection(List<ExtraServiceMapping> extraServiceMappingCollection) {
        this.extraServiceMappingCollection = extraServiceMappingCollection;
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
        if (!(object instanceof ExtraService)) {
            return false;
        }
        ExtraService other = (ExtraService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.ExtraService[ id=" + id + " ]";
    }
    
}

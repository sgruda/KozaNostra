package pl.lodz.p.it.ssbd2020.ssbd05.entities.mor;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "review", schema = "ssbd05schema", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"review_number"})
})
@TableGenerator(name = "ReviewIdGen", table = "id_generator", schema = "ssbd05schema",  pkColumnName = "class_name", pkColumnValue = "review", valueColumnName = "id_range")
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findById", query = "SELECT r FROM Review r WHERE r.id = :id"),
    @NamedQuery(name = "Review.findByContent", query = "SELECT r FROM Review r WHERE r.content = :content"),
    @NamedQuery(name = "Review.findByDate", query = "SELECT r FROM Review r WHERE r.date = :date"),
    @NamedQuery(name = "Review.findByVersion", query = "SELECT r FROM Review r WHERE r.version = :version"),
    @NamedQuery(name = "Review.findByReviewNumber", query = "SELECT r FROM Review r WHERE r.reviewNumber = :reviewNumber")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(lombok.AccessLevel.NONE)
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ReviewIdGen")
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "content", nullable = false, length = 512)
    private String content;

    @Basic(optional = false)
    @NotNull
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;

    @NotNull
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Client client;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 32, max = 32)
    @Column(name = "review_number", nullable = false, length = 32)
    private String reviewNumber;

    public Review() {
        this.reviewNumber = UUID.randomUUID().toString().replace("-", "");
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
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review[ id=" + id + " ]";
    }
    
}

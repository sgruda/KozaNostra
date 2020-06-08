package pl.lodz.p.it.ssbd2020.ssbd05.entities.mor;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
    @NamedQuery(name = "Review.findByReviewNumber", query = "SELECT r FROM Review r WHERE r.reviewNumber = :reviewNumber")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(lombok.AccessLevel.NONE)
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ReviewIdGen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Size(min = 1, max = 512, message = "{validation.size}")
    @Column(name = "content", nullable = false, length = 512)
    private String content;

    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter(lombok.AccessLevel.NONE)
    @Setter(lombok.AccessLevel.NONE)
    @Basic(optional = false)
    @Version
    @NotNull(message = "{validation.notnull}")
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private long version;

    @NotNull(message = "{validation.notnull}")
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Client client;

    @NotNull(message = "{validation.notnull}")
    @JoinColumn(name = "reservation_id", referencedColumnName = "id", nullable = false, updatable = false)
    @OneToOne(optional=false)
    private Reservation reservation;

    @Basic(optional = false)
    @NotNull(message = "{validation.notnull}")
    @Size(min = 32, max = 32, message = "{validation.size}")
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
        return "pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review[ id=" + id + " version=" + version + " ]";
    }
    
}

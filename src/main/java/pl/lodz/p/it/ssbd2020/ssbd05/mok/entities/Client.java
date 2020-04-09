package pl.lodz.p.it.ssbd2020.ssbd05.mok.entities;

import pl.lodz.p.it.ssbd2020.ssbd05.mor.entities.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.entities.Review;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@DiscriminatorValue("CLIENT")
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
        @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id")
})
public class Client extends AccessLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "client")
    private Collection<Review> reviewCollection = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "client")
    private Collection<Reservation> reservationCollection = new ArrayList<>();

    public Client() {
    }
}

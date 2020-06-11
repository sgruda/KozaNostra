package pl.lodz.p.it.ssbd2020.ssbd05.entities.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@DiscriminatorValue("CLIENT")
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
        @NamedQuery(name = "Client.findByLogin", query = "SELECT c FROM Client c WHERE c.account.login = :login"),
        @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id")
})
public class Client extends AccessLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "client")
    private Collection<Review> reviewCollection = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private Collection<Reservation> reservationCollection = new ArrayList<>();

    public Client() {
        super();
    }
}

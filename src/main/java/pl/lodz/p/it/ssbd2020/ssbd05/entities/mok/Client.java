package pl.lodz.p.it.ssbd2020.ssbd05.entities.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Klasa encyjna reprezentująca poziom dostępu Klient.
 */
@Entity
@DiscriminatorValue("CLIENT")
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
        @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
        @NamedQuery(name = "Client.findByLogin",query = "SELECT c from Client c where c.account.login = :login")
})
public class Client extends AccessLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "client")
    private Collection<Review> reviewCollection = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private Collection<Reservation> reservationCollection = new ArrayList<>();

    /**
     * Konstruktor bezparametrowy klasy Client.
     */
    public Client() {
        super();
    }
}

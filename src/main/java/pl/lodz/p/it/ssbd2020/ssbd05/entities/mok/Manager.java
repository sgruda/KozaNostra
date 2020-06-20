package pl.lodz.p.it.ssbd2020.ssbd05.entities.mok;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu Menadżer.
 */
@Entity
@DiscriminatorValue("MANAGER")
@NamedQueries({
        @NamedQuery(name = "Manager.findAll", query = "SELECT m FROM Manager m"),
        @NamedQuery(name = "Manager.findById", query = "SELECT m FROM Manager m WHERE m.id = :id")
})
public class Manager extends AccessLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor bezparametrowy klasy Manager.
     */
    public Manager() {
        super();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.entities.mok;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@DiscriminatorValue("ADMIN")
@NamedQueries({
        @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
        @NamedQuery(name = "Admin.findById", query = "SELECT a FROM Admin a WHERE a.id = :id")
})
public class Admin extends AccessLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    public Admin() {
        super();
    }
}

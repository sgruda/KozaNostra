package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

@Stateless(name = "AccountFacadeMOR")
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    public Optional<Account> findByLogin(String username) {
        return Optional.ofNullable(this.em.createNamedQuery("Account.findByLogin", Account.class)
                .setParameter("login", username).getSingleResult());
    }
}

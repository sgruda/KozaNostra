package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.web.mok.ListAccountsController;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Slf4j
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless(name = "AccountFacadeMOK")
@LocalBean
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd05mokPU")
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

    public Optional<Account> findByToken(String token) {
        return Optional.ofNullable(this.em.createNamedQuery("Account.findByToken", Account.class)
                .setParameter("token", token).getSingleResult());
    }

    public Collection<Account> filterAccounts(String accountFilter) {
        return em.createNamedQuery("Account.filterByNameAndSurname", Account.class)
                .setParameter("filter", accountFilter).getResultList();
    }

    @PermitAll
    public void create(Account entity){
        try{
            super.create(entity);
        }catch (DatabaseException ex){
            throw ex;
        }
    }

}

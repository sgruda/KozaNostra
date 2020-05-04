package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.EmailAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.LoginAlreadyExistsException;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import javax.persistence.PersistenceException;
import java.sql.SQLNonTransientConnectionException;
import java.util.Optional;

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
    public void create(Account entity) throws AppBaseException {
        try{
            super.create(entity);
        }catch (DatabaseException ex){
            if(ex.getCause() instanceof SQLNonTransientConnectionException){
                throw new DatabaseConnectionException(ex);
            }else{
                throw new DatabaseQueryException(ex);
            }
        }catch (PersistenceException e) {
            if (e.getMessage().contains("account_login_data_login_uindex")) {
                throw new LoginAlreadyExistsException(e);
            }if (e.getMessage().contains("account_personal_data_email_uindex")) {
                throw new EmailAlreadyExistsException(e);
            } else {
                throw new DatabaseQueryException(e);
            }
        }
    }
    @PermitAll
    public void edit(Account entity) {
        super.edit(entity);
    }
}

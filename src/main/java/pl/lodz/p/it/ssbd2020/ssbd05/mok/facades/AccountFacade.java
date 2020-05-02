package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import lombok.extern.java.Log;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountExceptions;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import pl.lodz.p.it.ssbd2020.ssbd05.web.auth.RegistrationController;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.security.auth.login.AccountException;
import javax.xml.crypto.Data;
import java.sql.SQLNonTransientConnectionException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless(name = "AccountFacadeMOK")
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

    @PermitAll
    public void create(Account entity) throws AppBaseException {
        try{
            super.create(entity);
        }catch (DatabaseException ex){
            if(ex.getCause() instanceof SQLNonTransientConnectionException){
                throw AppBaseException.DatabaseConnectionException(ex);
            }else{
                throw AppBaseException.DatabaseQueryException(ex);
            }
        }catch (PersistenceException e) {
            if (e.getMessage().contains("account_login_data_login_uindex")) {
                throw AccountExceptions.loginExistsException(e,entity);
            }if (e.getMessage().contains("account_personal_data_email_uindex")) {
                throw AccountExceptions.emailExistsException(e, entity);
            } else {
                throw AppBaseException.DatabaseQueryException(e);
            }
        }
    }
}

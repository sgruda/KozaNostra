package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.EmailAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.LoginAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.sql.SQLNonTransientConnectionException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless(name = "AccountFacadeMOK")
@LocalBean
@Interceptors(TrackerInterceptor.class)
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

    @Override
    @DenyAll
    public Optional<Account> find(Object id) {
        return super.find(id);
    }

    @Override
    @RolesAllowed("listAccounts")
    public List<Account> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @PermitAll
    public Optional<Account> findByLogin(String username) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("Account.findByLogin", Account.class)
                    .setParameter("login", username).getSingleResult());
        } catch (NoResultException noResultException) {
           throw new AccountNotFoundException(noResultException);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @PermitAll
    public Optional<Account> findByToken(String token) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("Account.findByToken", Account.class)
                    .setParameter("token", token).getSingleResult());
        } catch (NoResultException noResultException) {
            throw new AccountNotFoundException(noResultException);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @PermitAll
    public Optional<Account> findByMail(String mail) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("Account.findByEmail", Account.class)
                    .setParameter("email", mail).getSingleResult());
        } catch (NoResultException e) {
            throw new AccountNotFoundException(e);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @RolesAllowed("filterAccounts")
    public Collection<Account> filterAccounts(String accountFilter) throws AppBaseException {
        try {
            return em.createNamedQuery("Account.filterByNameAndSurname", Account.class)
                    .setParameter("filter", accountFilter).getResultList();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @PermitAll
    public void create(Account entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException ex) {
            if (ex.getCause() instanceof SQLNonTransientConnectionException) {
                throw new DatabaseConnectionException(ex);
            } else {
                throw new DatabaseQueryException(ex);
            }
        } catch (PersistenceException e) {
            if (e.getMessage().contains("account_login_data_login_uindex")) {
                throw new LoginAlreadyExistsException(e);
            }
            if (e.getMessage().contains("account_personal_data_email_uindex")) {
                throw new EmailAlreadyExistsException(e);
            } else {
                throw new DatabaseQueryException(e);
            }
        }
    }

    @Override
    @PermitAll
    public void edit(Account entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (DatabaseException ex) {
            if (ex.getCause() instanceof SQLNonTransientConnectionException) {
                throw new DatabaseConnectionException(ex);
            } else {
                throw new DatabaseQueryException(ex);
            }
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (PersistenceException e) {
            throw new DatabaseQueryException(e);
        }
    }

    @Override
    @DenyAll
    public void remove(Account entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

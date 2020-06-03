package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotHaveActiveAccessLevelsException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ChangeAccessLevelEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.StringUtils.collectionContainsIgnoreCase;

/**
 * Endpoint odpowiadający za zmianę poziomów dostępu
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ChangeAccessLevelEndpoint implements Serializable, ChangeAccessLevelEndpointLocal {

    /**
     * Manager konta
     */
    @Inject
    private AccountManager accountManager;
    /**
     * Konto
     */
    private Account account;

    /**
     * Znajdż konto po loginie
     *
     * @param username nazwa użytkownika
     * @return AccountDTO - konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed("findByLogin")
    public AccountDTO findByLogin(String username) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                account = accountManager.findByLogin(username);
                rollback = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + accountManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    /**
     * Zmień poziom dostępu danego konta
     *
     * @param accountDTO konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed("changeAccessLevel")
    public void changeAccessLevel(AccountDTO accountDTO) throws AppBaseException {
        Collection<AccessLevel> accessLevelCollection = account.getAccessLevelCollection();
        Collection<String> accessLevelStringCollection = accountDTO.getAccessLevelCollection();
        Properties properties =  ResourceBundles.loadProperties("config.user_roles.properties");
        int activeAccessLevels = 0;
        for (AccessLevel accessLevel : accessLevelCollection) {
            if (accessLevel instanceof Admin) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, properties.getProperty("roleAdmin")));
            } else if (accessLevel instanceof Manager) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, properties.getProperty("roleManager")));
            } else if (accessLevel instanceof Client) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, properties.getProperty("roleClient")));
            }
            if(accessLevel.getActive())
                activeAccessLevels++;
        }
        if(activeAccessLevels == 0)
            throw new AccountNotHaveActiveAccessLevelsException();

        account.setAccessLevelCollection(accessLevelCollection);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.edit(account);
                rollback = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.log(Level.SEVERE, "EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + accountManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}

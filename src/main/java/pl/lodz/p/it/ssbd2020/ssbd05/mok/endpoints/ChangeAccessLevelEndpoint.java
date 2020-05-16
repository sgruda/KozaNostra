package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ChangeAccessLevelEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.StringUtils.collectionContainsIgnoreCase;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ChangeAccessLevelEndpoint implements Serializable, ChangeAccessLevelEndpointLocal {
    @Inject
    private AccountManager accountManager;
    private Account account;

    @Override
    @RolesAllowed("findByLogin")
    public AccountDTO findByLogin(String username) throws AppBaseException {
        account = accountManager.findByLogin(username);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @Override
    @RolesAllowed("changeAccessLevel")
    public void changeAccessLevel(AccountDTO accountDTO) throws AppBaseException {
        log.info("WTF endpoint Dostalem " + accountDTO.getAccessLevelCollection());
        account = accountManager.findByLogin(accountDTO.getLogin());
        Collection<AccessLevel> accessLevelCollection = account.getAccessLevelCollection();
        Collection<String> accessLevelStringCollection = accountDTO.getAccessLevelCollection();
        Properties properties =  ResourceBundles.loadProperties("config.user_roles.properties");
        for (AccessLevel accessLevel : accessLevelCollection) {
            if (accessLevel instanceof Admin) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, properties.getProperty("roleAdmin")));
            } else if (accessLevel instanceof Manager) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, properties.getProperty("roleManager")));
            } else if (accessLevel instanceof Client) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, properties.getProperty("roleClient")));
            }
        }

        account.setAccessLevelCollection(accessLevelCollection);
        String temp = "";
        for(AccessLevel s : account.getAccessLevelCollection())
            temp += s.getAccessLevel() + " = " + s.getActive() + " + ";
        log.info("WTF, to zapisuje " + temp);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.edit(account);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.log(Level.SEVERE, "EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}

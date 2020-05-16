package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AddAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class AddAccountEndpoint implements AddAccountEndpointLocal {

    @Inject
    private AccountManager accountManager;
    private Account account;

    @Override
    @RolesAllowed("addAccount")
    public void addAccount(AccountDTO accountDTO) throws AppBaseException {
        account = AccountMapper.INSTANCE.createNewAccount(accountDTO);
        account.setAccessLevelCollection(generateAccessLevels(accountDTO));
        account.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        PreviousPassword previousPassword = new PreviousPassword();
        previousPassword.setPassword(account.getPassword());
        previousPassword.setAccount(account);
        account.getPreviousPasswordCollection().add(previousPassword);

        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.createAccount(account);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < 5); //TODO po merge: ResourceBundles.getTransactionRepeatLimit()
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    private Collection<AccessLevel> generateAccessLevels(AccountDTO accountDTO) throws AppBaseException {
        List<AccessLevel> accessLevels = new ArrayList<>();
        Properties properties =  ResourceBundles.loadProperties("config.user_roles.properties");

        AccessLevel client = new Client();
        client.setAccount(account);
        client.setAccessLevel(properties.getProperty("roleClient"));
        client.setActive(false);
        accessLevels.add(client);

        AccessLevel manager = new Manager();
        manager.setAccount(account);
        manager.setAccessLevel(properties.getProperty("roleManager"));
        manager.setActive(false);
        accessLevels.add(manager);

        AccessLevel admin = new Admin();
        admin.setAccount(account);
        admin.setAccessLevel(properties.getProperty("roleAdmin"));
        admin.setActive(false);
        accessLevels.add(admin);

        for (String accessLevelStr : accountDTO.getAccessLevelCollection()) {
            if (accessLevelStr.equals(properties.getProperty("roleClient"))) {
                accessLevels.get(accessLevels.indexOf(client)).setActive(true);
            } else if (accessLevelStr.equals(properties.getProperty("roleManager"))) {
                accessLevels.get(accessLevels.indexOf(manager)).setActive(true);
            } else if (accessLevelStr.equals(properties.getProperty("roleAdmin"))) {
                accessLevels.get(accessLevels.indexOf(admin)).setActive(true);
            }
        }
        return accessLevels;
    }
}

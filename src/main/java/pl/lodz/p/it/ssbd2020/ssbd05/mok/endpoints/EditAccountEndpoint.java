package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.PreviousPassword;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;

import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import java.util.Collection;
import java.util.Properties;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.StringUtils.collectionContainsIgnoreCase;

@Slf4j
@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class EditAccountEndpoint implements Serializable {
    @Inject
    private AccountManager accountManager;
    private Account account;

    @RolesAllowed("findByLogin")
    public AccountDTO findByLogin(String username) throws ExceededTransactionRetriesException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                account = accountManager.findByLogin(username);
                rollback = accountManager.isLastTransactionRollback();
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @RolesAllowed({"changeOtherAccountPassword","changeOwnAccountPassword"})
    public void changePassword(String newPassword, AccountDTO accountDTO) throws AppBaseException {
        for (PreviousPassword psw: account.getPreviousPasswordCollection()){
            if(psw.getPassword().equals(HashGenerator.sha256(newPassword))){
                throw new AccountPasswordAlreadyUsedException();
            }
        }
        account.setPassword(HashGenerator.sha256(newPassword));
        PreviousPassword previousPassword = new PreviousPassword();
        previousPassword.setAccount(account);
        previousPassword.setPassword(HashGenerator.sha256(newPassword));
        account.getPreviousPasswordCollection().add(previousPassword);

        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.edit(account);
                rollback = accountManager.isLastTransactionRollback();
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @RolesAllowed("editOtherAccount")
    public void edit(AccountDTO accountDTO) throws AppBaseException {
        Collection<AccessLevel> accessLevelCollection = account.getAccessLevelCollection();
        Collection<String> accessLevelStringCollection = accountDTO.getAccessLevelCollection();
        AccountMapper.INSTANCE.updateAccountFromDTO(accountDTO, account);
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

        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.edit(account);
                rollback = accountManager.isLastTransactionRollback();
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
    @PermitAll
    public void blockAccount(AccountDTO accountDTO) throws AppBaseException {
        boolean rollback;
        int callCounter = 0;
        do {
            try {
                accountManager.blockAccount(account);
                rollback = accountManager.isLastTransactionRollback();
                callCounter++;
            }
            catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendBlockedAccountEmail(account.getEmail());
        }
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @RolesAllowed("unlockAccount")
    public void unlockAccount(AccountDTO accountDTO) throws AppBaseException {
        boolean rollback;
        int callCounter = 0;
        do {
            try {
                accountManager.unlockAccount(account);
                rollback = accountManager.isLastTransactionRollback();
                callCounter++;
            }
            catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendUnlockedAccountEmail(account.getEmail());
        }
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}

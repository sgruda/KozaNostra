package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.PreviousPassword;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

/**
 * Punkt dostępowy odpowiedzialny za edycję konta użytkownika, dostarcza implementację interfejsu EditAccountEndpointLocal
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class EditAccountEndpoint implements Serializable, EditAccountEndpointLocal {

    @Inject
    private AccountManager accountManager;
    private Account account;

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

    @Override
    @RolesAllowed({"changeOwnAccountPassword"})
    public void changePassword(AccountDTO accountDTO) throws AppBaseException {
        for (PreviousPassword psw : account.getPreviousPasswordCollection()) {
            if (psw.getPassword().equals(HashGenerator.sha256(accountDTO.getPassword()))) {
                throw new AccountPasswordAlreadyUsedException();
            }
        }
        account.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        PreviousPassword previousPassword = new PreviousPassword();
        previousPassword.setAccount(account);
        previousPassword.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        account.getPreviousPasswordCollection().add(previousPassword);

        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.edit(account);
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
    }

    @Override
    @RolesAllowed({"changeOtherAccountPassword"})
    public void changeOtherAccountPassword(AccountDTO accountDTO) throws AppBaseException {
        boolean doesExists = false;
        for (PreviousPassword psw: account.getPreviousPasswordCollection()){
            if(psw.getPassword().equals(HashGenerator.sha256(accountDTO.getPassword()))){
                doesExists = true;
            }
        }
        if(!doesExists){
            account.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
            PreviousPassword previousPassword = new PreviousPassword();
            previousPassword.setAccount(account);
            previousPassword.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
            account.getPreviousPasswordCollection().add(previousPassword);
        } else {
            account.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        }
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.edit(account);
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
    }

    @Override
    @RolesAllowed({"editOwnAccount","editOtherAccount"})
    public void editAccount(AccountDTO accountDTO) throws AppBaseException {
        AccountMapper.INSTANCE.updateAccountFromDTO(accountDTO, account);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.edit(account);
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
    }

    @Override
    @RolesAllowed("blockAccount")
    public void blockAccount(AccountDTO accountDTO) throws AppBaseException {
        boolean rollback;
        int callCounter = 0;
        do {
            try {
                accountManager.blockAccount(account);
                rollback = accountManager.isLastTransactionRollback();
            }
            catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + accountManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendBlockedAccountEmail(account.getEmail());
        }
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @Override
    @RolesAllowed("unlockAccount")
    public void unlockAccount(AccountDTO accountDTO) throws AppBaseException {
        boolean rollback;
        int callCounter = 0;
        do {
            try {
                accountManager.unlockAccount(account);
                rollback = accountManager.isLastTransactionRollback();
            }
            catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + accountManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendUnlockedAccountEmail(account.getEmail());
        }
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}

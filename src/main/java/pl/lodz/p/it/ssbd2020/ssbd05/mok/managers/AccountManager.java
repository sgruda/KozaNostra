package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.rmi.RemoteException;
import java.util.Collection;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
public class AccountManager  implements SessionSynchronization {
    @Inject
    private AccountFacade accountFacade;

    private long txId;
    @Getter
    @Setter
    private boolean lastTransactionRollback;
    private static final Logger loger = Logger.getLogger(AccountManager.class.getName());

    //TODO Bedzie ta metoda gdzies uzywana ?
    public Account findById(Long id) {
        if (accountFacade.find(id).isPresent())
            return accountFacade.find(id).get();
        else throw new IllegalArgumentException("Nie ma konta o takim ID");
    }

    @PermitAll
    public Account findByLogin(String login) throws AccountNotFoundException {
        try {
            return accountFacade.findByLogin(login).get();
        } catch (AccountNotFoundException e) {
            log.warn(e.getClass().getName() + " " + e.getMessage());
            throw new AccountNotFoundException(e);
        }
    }

    @PermitAll
    public Account findByToken(String token) throws AppBaseException {
        if(accountFacade.findByToken(token).isPresent())
            return accountFacade.findByToken(token).get();
        else throw new AppBaseException(ResourceBundles.getTranslatedText("error.default"));
    }

    @PermitAll
    public void edit(Account account) throws AppBaseException {
        accountFacade.edit(account);
    }

    @PermitAll
    public void confirmAccount(Account account) throws AppBaseException {
        if(!account.isConfirmed()) {
            account.setConfirmed(true);
            accountFacade.edit(account);
        }
        else throw new AccountAlreadyConfirmedException(ResourceBundles.getTranslatedText("error.account.confirmed"));
    }

    @PermitAll
    public void createAccount(Account account) throws AppBaseException {
        accountFacade.create(account);
    }

    @RolesAllowed("listAccounts")
    public Collection<Account> getAllAccounts() {
        return accountFacade.findAll();
    }
    @RolesAllowed("filterAccounts")
    public Collection<Account> filterAccounts(String accountFilter) {
        return accountFacade.filterAccounts(accountFilter);
    }
    @RolesAllowed("blockAccount")
    public void blockAccount(Account account) throws AppBaseException {
        account.setActive(false);
        accountFacade.edit(account);
    }

    @RolesAllowed("unlockAccount")
    public void unlockAccount(Account account) throws AppBaseException {
        account.setActive(true);
        account.setFailedAuthCounter(0);
        accountFacade.edit(account);
    }

    @Override
    public void afterBegin() throws EJBException, RemoteException {
        txId = System.currentTimeMillis();
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " zostala rozpoczeta");
    }

    @Override
    public void beforeCompletion() throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " przed zakonczeniem");
    }

    @Override
    public void afterCompletion(boolean committed) throws EJBException, RemoteException {
        lastTransactionRollback = !committed;
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " zostala zakonczona przez: " + (committed?"zatwierdzenie":"wycofanie"));
    }
}

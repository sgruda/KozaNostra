package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.PreviousPasswordFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.ejb.*;
import javax.inject.Inject;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
public class AccountManager  implements SessionSynchronization {
    @Inject
    private AccountFacade accountFacade;
    @Inject
    private PreviousPasswordFacade previousPasswordFacade;

    private long txId;
    @Getter
    @Setter
    private boolean lastTransactionRollback;
    private static final Logger loger = Logger.getLogger(AccountManager.class.getName());

    public Account findById(Long id) {
        if (accountFacade.find(id).isPresent())
            return accountFacade.find(id).get();
        else throw new IllegalArgumentException("Nie ma konta o takim ID");
    }

    public Account findByLogin(String login) {
        if(accountFacade.findByLogin(login).isPresent())
            return accountFacade.findByLogin(login).get();
        else throw new IllegalArgumentException("Konto o podanym loginie nie istnieje");
    }

    public Account findByToken(String token) throws AppBaseException {
        if(accountFacade.findByToken(token).isPresent())
            return accountFacade.findByToken(token).get();
        else throw new AppBaseException(ResourceBundles.getTranslatedText("error.default"));
    }


    public void edit(Account account) throws AppBaseException {
        accountFacade.edit(account);
    }

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

    public Collection<Account> getAllAccounts() {
        if(Optional.ofNullable(accountFacade.findAll()).isPresent())
            return accountFacade.findAll();
        else throw new IllegalArgumentException(ResourceBundles.getTranslatedText("error.account.blocked"));
    }

    public void blockAccount(Account account) throws AppBaseException {
        account.setActive(false);
        accountFacade.edit(account);
    }
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

    public Collection<Account> filterAccounts(String accountFilter){
        if(Optional.ofNullable(accountFacade.filterAccounts(accountFilter)).isPresent()) {
            return accountFacade.filterAccounts(accountFilter);
        }
        else throw new IllegalArgumentException("Nie ma kont pasujących do tego filtru");
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;

import javax.ejb.*;
import javax.inject.Inject;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class AccountManager  implements SessionSynchronization {
    @Inject
    private AccountFacade accountFacade;
    private long txId;

    @Getter
    @Setter
    private boolean lastTransactionRollback;

    private static final Logger loger = Logger.getLogger(AccountManager.class.getName());

    //TODO: stworzenie własnych wyjątków i obsługa ich

    public Account findById(Long id) {
        if(accountFacade.find(id).isPresent())
            return accountFacade.find(id).get();
        else throw new IllegalArgumentException("Nie ma konta o takim ID");
    }

    public Account findByLogin(String login) {
        if(accountFacade.findByLogin(login).isPresent())
            return accountFacade.findByLogin(login).get();
        else throw new IllegalArgumentException("Konto o podanym loginie nie istnieje");
    }

    public void edit(Account account) {
        accountFacade.edit(account);
    }

    public void createAccount(Account account) throws AppBaseException {
        accountFacade.create(account);
        EmailSender emailSender = new EmailSender();
        emailSender.sendRegistrationEmail(account.getEmail(), account.getVeryficationToken(), account.getLogin());
    }

    public Collection<Account> getAllAccounts() {
        if(Optional.ofNullable(accountFacade.findAll()).isPresent())
            return accountFacade.findAll();
        else throw new IllegalArgumentException("Nie ma żadnych kont w bazie");
    }

    public void unlockAccount(Account account) {
        account.setActive(true);
        account.setFailedAuthCounter(0);
        this.edit(account);
        //TODO wysylanie maila
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

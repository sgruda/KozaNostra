package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;


@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class AccountManager {
    @Inject
    private AccountFacade accountFacade;

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

    public Account findByToken(String token) throws AppBaseException {
        if(accountFacade.findByToken(token).isPresent())
            return accountFacade.findByToken(token).get();
        else throw new AppBaseException(ResourceBundles.getTranslatedText("error.default"));
    }

    public void edit(Account account) {
        accountFacade.edit(account);
    }

    public void createAccount(Account account) {

        accountFacade.create(account);
        EmailSender emailSender = new EmailSender();
        emailSender.sendRegistrationEmail(account.getEmail(), account.getVeryficationToken());
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
}

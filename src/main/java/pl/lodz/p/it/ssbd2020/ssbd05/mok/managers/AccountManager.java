package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountBlockedException;
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
    @Inject
    private EmailSender emailSender;

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

    public void confirmAccount(Account account) throws AccountAlreadyConfirmedException {
        if(!account.isConfirmed()) {
            account.setConfirmed(true);
            accountFacade.edit(account);
        }
        else throw new AccountAlreadyConfirmedException(ResourceBundles.getTranslatedText("error.account.confirmed"));
    }

    public void createAccount(Account account) {
        accountFacade.create(account);
        emailSender.sendRegistrationEmail(account.getEmail(), account.getVeryficationToken());
    }

    public Collection<Account> getAllAccounts() {
        if(Optional.ofNullable(accountFacade.findAll()).isPresent())
            return accountFacade.findAll();
        else throw new IllegalArgumentException(ResourceBundles.getTranslatedText("error.account.blocked"));
    }

    public void blockAccount(Account account) throws AccountBlockedException {
        account.setActive(false);
        accountFacade.edit(account);
        emailSender.sendBlockedAccountEmail(account.getEmail());
        throw new AccountBlockedException("Account was blocked");
    }

    public void unlockAccount(Account account) {
        account.setActive(true);
        account.setFailedAuthCounter(0);
        accountFacade.edit(account);
        emailSender.sendUnlockedAccountEmail(account.getEmail());
    }
}

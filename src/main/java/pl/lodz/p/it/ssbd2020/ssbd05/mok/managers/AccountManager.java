package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Collection;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
public class AccountManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private AccountFacade accountFacade;

    @PermitAll
    public Account findByLogin(String login) {
        if(accountFacade.findByLogin(login).isPresent())
            return accountFacade.findByLogin(login).get();
        else throw new IllegalArgumentException("Konto o podanym loginie nie istnieje");
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
    public Collection<Account> getAllAccounts() throws AppBaseException {
        return accountFacade.findAll();
    }

    @RolesAllowed("filterAccounts")
    public Collection<Account> filterAccounts(String accountFilter) throws AppBaseException {
        return accountFacade.filterAccounts(accountFilter);
    }

    @PermitAll
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
}

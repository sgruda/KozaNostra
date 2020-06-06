package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.ForgotPasswordToken;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.ForgotPasswordTokenFacade;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Collection;

/**
 * Manager odpowiadający za operację na encji typu Account
 */
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class AccountManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private AccountFacade accountFacade;
    @Inject
    private ForgotPasswordTokenFacade forgotPasswordTokenFacade;
    @Inject
    private AccessLevelFacade accessLevelFacade;

    /**
     * Wyszukaj konto po loginie.
     *
     * @param login login
     * @return Account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public Account findByLogin(String login) throws AppBaseException {
        try {
            return accountFacade.findByLogin(login).get();
        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException(e);
        }
    }

    /**
     * Wyszukaj po tokenie
     *
     * @param token token
     * @return Account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public Account findByToken(String token) throws AppBaseException {
        if(accountFacade.findByToken(token).isPresent())
            return accountFacade.findByToken(token).get();
        else throw new AppBaseException("error.default");
    }

    /**
     * Wyszukaj konto po mailu
     *
     * @param mail mail
     * @return account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public Account findByMail(String mail) throws AppBaseException {
        if(accountFacade.findByMail(mail).isPresent())
            return accountFacade.findByMail(mail).get();
        else throw new AccountNotFoundException("error.account.not.found");
    }

    /**
     * Edytuj konto
     *
     * @param account Konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public void edit(Account account) throws AppBaseException {
        accountFacade.edit(account);
        for(AccessLevel accessLevel : account.getAccessLevelCollection())
            accessLevelFacade.edit(accessLevel);
    }

    /**
     * Potwierdź konto
     *
     * @param account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public void confirmAccount(Account account) throws AppBaseException {
        if(!account.isConfirmed()) {
            account.setConfirmed(true);
            accountFacade.edit(account);
        }
        else throw new AccountAlreadyConfirmedException("error.account.confirmed");
    }

    /**
     * Stwórz konto
     *
     * @param account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public void createAccount(Account account) throws AppBaseException {
        accountFacade.create(account);
    }

    /**
     * Pobierz wszystkie konta
     *
     * @return Collection kolekcja kont
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @RolesAllowed("listAccounts")
    public Collection<Account> getAllAccounts() throws AppBaseException {
        return accountFacade.findAll();
    }

    /**
     * Filtruj po kontach
     *
     * @param accountFilter filtr
     * @return Collection<Account>
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @RolesAllowed("filterAccounts")
    public Collection<Account> filterAccounts(String accountFilter) throws AppBaseException {
        return accountFacade.filterAccounts(accountFilter);
    }

    /**
     * Zablokuj konto
     *
     * @param account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @RolesAllowed("blockAccount")
    public void blockAccount(Account account) throws AppBaseException {
        account.setActive(false);
        accountFacade.edit(account);
    }

    /**
     * Odblokuj konto
     *
     * @param account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @RolesAllowed("unlockAccount")
    public void unlockAccount(Account account) throws AppBaseException {
        account.setActive(true);
        account.setFailedAuthCounter(0);
        accountFacade.edit(account);
    }

    /**
     * Stwórz obiekt typu forgotPasswordToken. Token używany do resetowania hasła
     *
     * @param forgotPasswordToken Token do resetowania hasła
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public void createForgotPasswordToken(ForgotPasswordToken forgotPasswordToken) throws AppBaseException {
        forgotPasswordTokenFacade.create(forgotPasswordToken);
    }

    /**
     * Wyszukaj token po hashu
     *
     * @param hash hash
     * @return obiekt typu ForgotPasswordToken
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public ForgotPasswordToken findTokenByHash(String hash) throws AppBaseException {
        if (forgotPasswordTokenFacade.findByHash(hash).isPresent())
            return forgotPasswordTokenFacade.findByHash(hash).get();
        else throw new AppBaseException("error.default");
    }

    /**
     * Ustaw hasło po resecie
     *
     * @param account konto
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public void setPasswordAfterReset(Account account) throws AppBaseException {
        if(account.isConfirmed() && account.isActive())
            accountFacade.edit(account);
        else throw new AppBaseException("error.default");
    }

    /**
     * Pobierz wszystkie tokeny
     *
     * @return Collection<ForgotPasswordToken>
     * @throws AppBaseException the app base exception
     */
    @PermitAll
    public Collection<ForgotPasswordToken> getAllTokens() throws AppBaseException {
        return forgotPasswordTokenFacade.findAll();
    }

    /**
     * Usuń poprzedni token
     *
     * @param forgotPasswordToken Obiekt typu forgotPasswordToken
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @PermitAll
    public void deletePreviousToken(ForgotPasswordToken forgotPasswordToken) throws AppBaseException {
        forgotPasswordTokenFacade.remove(forgotPasswordToken);
    }
}

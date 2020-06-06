package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Kontroler odpowiedzialny za blokowanie i odblokowywanie konta użytkownika
 */
@Log
@Named
@ViewScoped
public class ActivationAccountController implements Serializable {
    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    private AccountDTO account;

    /**
     * Metoda odpowiedzialna za pobranie obiektu konta
     *
     * @return obiekt typu Account
     */
    public AccountDTO getAccount() {
        return account;
    }

    /**
     * Metoda odpowiedzialna za wybranie odpowiedniego konta
     *
     * @param account obiekt typu AccountDTO
     */
    public void setAccount(AccountDTO account) {
        this.account = account;
        try {
            editAccountEndpointLocal.findByLogin(account.getLogin());
        } catch(AppOptimisticLockException e ) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null,"error.changeotherpassword.optimisticlock");
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null,e.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za odblokowanie konta
     *
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    //throws, bo wywołujemy w innym kontrolerze i obsługa jest tam
    public void unlockAccount() throws AppBaseException {
        editAccountEndpointLocal.unlockAccount(account);
        ResourceBundles.emitMessageWithFlash(null,"page.accountdetails.unlock");
    }

    /**
     * Metoda odpowiedzialna za blokowanie konta
     *
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    public void blockAccount() throws AppBaseException {
        editAccountEndpointLocal.blockAccount(account);
        ResourceBundles.emitMessageWithFlash(null,"page.accountdetails.blocked");
    }
}

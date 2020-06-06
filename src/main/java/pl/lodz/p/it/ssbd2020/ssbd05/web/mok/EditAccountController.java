package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Kontroler wykorzystywany przy edycji własnego konta.
 */
@Log
@Named
@ViewScoped
public class EditAccountController implements Serializable {

    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    @Getter
    @Setter
    private AccountDTO accountDTO;

    /**
     * Metoda inicjulizująca, pobiera obiekt DTO edytowanego konta
     */
    @PostConstruct
    public void init() {
        try {
            accountDTO = editAccountEndpointLocal.findByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        } catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ResourceBundles.getTranslatedText(ex.getMessage()));
        }
    }

    /**
     * Metoda odpowiedzialna za edycje własnego konta.
     */
    public void editAccount() {
        try {
            editAccountEndpointLocal.editAccount(accountDTO);
            ResourceBundles.emitMessageWithFlash(null,"page.edit.account.message");
        } catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.account.optimisticlock");
        } catch (ValidationException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageByPlainText(null, ex.getMessage());
        } catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        }
    }

    /**
     * Metoda odpowiedzialna za przenoszenie na stronę szczegółów własnego konta
     *
     * @return string
     */
    public String goBack() {
        return "accountDetails";
    }
}

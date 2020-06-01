package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotHaveActiveAccessLevelsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ChangeAccessLevelEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.StringUtils.collectionContainsIgnoreCase;

/**
 * Kontroler odpowiedzialny za operacje na końcie
 */
@Log
@Named
@ViewScoped
public class AccountDetailsController implements Serializable {

    /**
     * Konto odpowiadające za pobranie konta
     */
    @Inject
    private AccountDetailsEndpointLocal accountDetailsEndpointLocal;
    /**
     * Konto
     */
    @Getter
    private AccountDTO account;
    /**
     * Kontroler odpowiadający za aktywację kont
     */
    @Inject
    private ActivationAccountController activationAccountController;
    /**
     * Endpoint służący do zmiany poziomu dostępu
     */
    @Inject
    private ChangeAccessLevelEndpointLocal changeAccessLevelEndpointLocal;
    /**
     * obiekt typu Properties
     */
    private Properties roleProperties;
    /**
     * boolean roleAdminActive
     */
    @Getter
    @Setter
    private boolean roleAdminActive;
    /**
     * boolean roleManagerActive
     */
    @Getter
    @Setter
    private boolean roleManagerActive;
    /**
     * boolean roleClientActive
     */
    @Getter
    @Setter
    private boolean roleClientActive;

    /**
     * Zainicjuj kontroler
     */
    @PostConstruct
    public void init() {
        String selectedLogin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedLogin");
        try {
            this.account = accountDetailsEndpointLocal.getAccount(selectedLogin);
            changeAccessLevelEndpointLocal.findByLogin(selectedLogin);
            activationAccountController.setAccount(this.account);
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
        this.setRolesInfo(this.account.getAccessLevelCollection());
    }

    /**
     * Przejdź do formularza edycji konta
     *
     * @return the string
     */
    public String goToEditForm() {
        return "editAccount";
    }

    /**
     * Przejdź do formularza zmiany hasła
     *
     * @return string
     */
    public String goToPasswordChange(){return "passwordForm";}

    /**
     * Wróć na poprzednią stronę
     *
     * @return string
     */
    public String goBack() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedLogin");
        return "goBack";
    }

    /**
     * Odblokuj konto
     */
    public void unlockAccount()  {
        try {
            activationAccountController.unlockAccount();
            refresh();
        } catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, "error.account.optimisticlock.refresh");
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        }
    }

    /**
     * Zablokuj konto.
     */
    public void blockAccount() {
        try{
            activationAccountController.blockAccount();
            refresh();
        }catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, "error.account.optimisticlock.refresh");
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessage(null, "error.default");
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        }
    }

    /**
     * Odśwież stronę
     */
    public void refresh() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
            this.account = accountDetailsEndpointLocal.getAccount(account.getLogin());
        } catch (AppBaseException | IOException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }

    /**
     * Zmień poziom dostępu
     */
    public void changeAccessLevels() {
        Collection<String> accessLevels = new ArrayList<>();
        Collection<String> accessLevelsBackup = account.getAccessLevelCollection();
        if(roleClientActive)
            accessLevels.add(roleProperties.getProperty("roleClient"));
        if(roleManagerActive)
            accessLevels.add(roleProperties.getProperty("roleManager"));
        if(roleAdminActive)
            accessLevels.add(roleProperties.getProperty("roleAdmin"));
        account.setAccessLevelCollection(accessLevels);
        try {
            changeAccessLevelEndpointLocal.changeAccessLevel(account);
            ResourceBundles.emitMessageWithFlash(null, "page.accountdetails.accessLevel.success");
        } catch(AccountNotHaveActiveAccessLevelsException e) {
            account.setAccessLevelCollection(accessLevelsBackup);
            this.setRolesInfo(accessLevelsBackup);
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.account.not.have.active.access.levels");
        } catch (AppOptimisticLockException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.account.optimisticlock");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
        refresh();
    }

    /**
     * Ustaw role konta
     *
     * @param accessLevelStringCollection kolekcja obiektów typu AccessLevel
     */
    private void setRolesInfo(Collection<String> accessLevelStringCollection) {
        roleProperties = null;
        try {
            roleProperties = ResourceBundles.loadProperties("config.user_roles.properties");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
        roleManagerActive = false;
        roleAdminActive = false;
        roleClientActive = false;
        if(collectionContainsIgnoreCase(accessLevelStringCollection, roleProperties.getProperty("roleClient"))) {
            roleClientActive = true;
        }
        if(collectionContainsIgnoreCase(accessLevelStringCollection, roleProperties.getProperty("roleManager"))) {
            roleManagerActive = true;
        }
        if(collectionContainsIgnoreCase(accessLevelStringCollection, roleProperties.getProperty("roleAdmin"))) {
            roleAdminActive = true;
        }
    }
}

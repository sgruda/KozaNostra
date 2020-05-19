package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotHaveActiveAccessLevelsException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ChangeAccessLevelEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.*;
import java.util.logging.Level;
import static pl.lodz.p.it.ssbd2020.ssbd05.utils.StringUtils.collectionContainsIgnoreCase;

@Log
@Named
@ConversationScoped
public class AccountDetailsController implements Serializable {

    @Inject
    private AccountDetailsEndpointLocal accountDetailsEndpointLocal;
    @Inject
    private Conversation conversation;
    @Getter
    private AccountDTO account;
    @Inject
    private ActivationAccountController activationAccountController;
    @Inject
    private ChangeAccessLevelEndpointLocal changeAccessLevelEndpointLocal;
    private Properties roleProperties;
    @Getter
    @Setter
    private boolean roleAdminActive;
    @Getter
    @Setter
    private boolean roleManagerActive;
    @Getter
    @Setter
    private boolean roleClientActive;

    public String selectAccount(AccountDTO accountDTO) {
        conversation.begin();
        try {
            this.account = accountDetailsEndpointLocal.getAccount(accountDTO.getLogin());
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        this.setRolesInfo(this.account.getAccessLevelCollection());
        return "accountDetails";
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
    
    public String getAccountDetailsConversationID(){
        return conversation.getId();
    }

    public void unlockAccount()  {
        try {
            activationAccountController.unlockAccount(account);
            refresh();
        } catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }

    public void blockAccount() {
        try{
            activationAccountController.blockAccount(account);
            refresh();
        }catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }

    public void refresh() {
        try {
            this.account = accountDetailsEndpointLocal.getAccount(account.getLogin());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public void changeAccessLevels() {
        Collection<String> accessLevels = new LinkedList<>();
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
        } catch(AccountNotHaveActiveAccessLevelsException e) {
            account.setAccessLevelCollection(accessLevelsBackup);
            this.setRolesInfo(accessLevelsBackup);
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.account.not.have.active.access.levels");
        } catch (AppBaseException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.simple");
        }
    }

    private void setRolesInfo(Collection<String> accessLevelStringCollection) {
        roleProperties = null;
        try {
            roleProperties = ResourceBundles.loadProperties("config.user_roles.properties");
        } catch (AppBaseException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, "error.simple");
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

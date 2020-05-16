package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotHaveActiveAccessLevelsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ChangeAccessLevelEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
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

    public String selectAccount(AccountDTO accountDTO) throws AppBaseException {
        conversation.begin();
        this.account = accountDetailsEndpointLocal.getAccount(accountDTO.getLogin());
        this.setRolesInfo();
        return "accountDetails";
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
    
    public String getAccountDetailsConversationID(){
        return conversation.getId();
    }

    @RolesAllowed(value = "ADMIN")
    public void unlockAccount() throws AppBaseException {
        activationAccountController.unlockAccount(account);
        //TODO jakas obsluga wyjatkow?
        //activationAccountController ja zapewni, tylko czy aby na pewno
        refresh();
    }
    @RolesAllowed(value = "ADMIN")
    public void blockAccount() throws AppBaseException {
        activationAccountController.blockAccount(account);
        //TODO jakas obsluga wyjatkow?
        //activationAccountController ja zapewni, tylko czy aby na pewno
        refresh();
    }
    public void refresh() throws AppBaseException {
        this.account = accountDetailsEndpointLocal.getAccount(account.getLogin());
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
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.account.not.have.active.access.levels");
        } catch (AppBaseException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.simple");
        }
        try {
            refresh();
        } catch (AppBaseException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.simple");
        }
    }
    private void setRolesInfo() {
        roleProperties = null;
        try {
            roleProperties = ResourceBundles.loadProperties("config.user_roles.properties");
        } catch (AppBaseException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, "error.simple");
        }
        Collection<String> accessLevelStringCollection = account.getAccessLevelCollection();
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

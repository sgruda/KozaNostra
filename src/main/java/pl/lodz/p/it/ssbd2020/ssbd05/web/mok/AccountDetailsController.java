package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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

    public String selectAccount(AccountDTO accountDTO) {
        conversation.begin();
        try {
            this.account = accountDetailsEndpointLocal.getAccount(accountDTO.getLogin());
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        return "accountDetails";
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
    
    public String getAccountDetailsConversationID(){
        return conversation.getId();
    }

    public void unlockAccount() {
        activationAccountController.unlockAccount(account);
        //TODO jakas obsluga wyjatkow?
        //activationAccountController ja zapewni, tylko czy aby na pewno
        refresh();
    }

    public void blockAccount() {
        activationAccountController.blockAccount(account);
        //TODO jakas obsluga wyjatkow?
        //activationAccountController ja zapewni, tylko czy aby na pewno
        refresh();
    }

    public void refresh() {
        try {
            this.account = accountDetailsEndpointLocal.getAccount(account.getLogin());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
}

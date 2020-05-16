package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;


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

    public String selectAccount(AccountDTO accountDTO) throws AppBaseException {
        conversation.begin();
        this.account = accountDetailsEndpointLocal.getAccount(accountDTO.getLogin());
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

    @RolesAllowed(value = "ADMIN")
    public void changeOtherAccountPassword(String password){
//        try {
//            editAccountEndpointLocal.changePassword(password,this.account);
//        } catch (AppBaseException appBaseException) {
//            log.severe(appBaseException.getClass().toString());
//        }
    }


}

package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.AccountDetailsEndpoint;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ConversationScoped
@RolesAllowed(value = "ADMIN")
public class AccountDetailsController implements Serializable {

    @Inject
    private AccountDetailsEndpoint accountDetailsEndpoint;
    @Inject
    private Conversation conversation;
    @Getter
    private AccountDTO account;
    @Inject
    private ActivationAccountController activationAccountController;

    public String selectAccount(AccountDTO accountDTO) {
        conversation.begin();
        this.account = accountDetailsEndpoint.getAccount(accountDTO.getLogin());
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
    public void blockAccount() {
        activationAccountController.blockAccount(account);
        //TODO jakas obsluga wyjatkow?
        //activationAccountController ja zapewni, tylko czy aby na pewno
        refresh();
    }
    public void refresh() {
        this.account = accountDetailsEndpoint.getAccount(account.getLogin());
    }
}

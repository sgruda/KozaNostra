package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.AccountDetailsEndpoint;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ConversationScoped
@RolesAllowed(value = "ADMIN")
@Slf4j
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
        log.info("No siema kontoDTO to " + accountDTO);
        conversation.begin();
        this.account = accountDetailsEndpoint.getAccount(accountDTO.getLogin());
        log.info("No siema konto to " + account);
        return "accountDetails";
    }

    public String formatDate(Date date) {
        if(date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
    
    public String getAccountDetailsConversationID(){
        return conversation.getId();
    }

    @RolesAllowed(value = "ADMIN")
    public void unlockAccount() {
        activationAccountController.unlockAccount(account);
        //TODO jakas obsluga wyjatkow?
        refresh();
    }
    @RolesAllowed(value = "ADMIN")
    public void blockAccount() {
        activationAccountController.blockAccount(account);
        //TODO jakas obsluga wyjatkow?
        refresh();
    }
    public void refresh() {
        this.account = accountDetailsEndpoint.getAccount(account.getLogin());
    }
}

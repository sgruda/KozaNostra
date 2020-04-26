package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.AccountDetailsEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.EditAccountEndpoint;

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
public class AccountDetailsController implements Serializable {

    @Inject
    private AccountDetailsEndpoint accountDetailsEndpoint;
    @Inject
    private EditAccountEndpoint editAccountEndpoint;
    @Inject
    private Conversation conversation;
    @Getter
    private AccountDTO account;

    public String selectAccount(AccountDTO accountDTO) {
        conversation.begin();
        this.account = accountDetailsEndpoint.getAccount(accountDTO.getId());
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

    public void unlockAccount() {
        editAccountEndpoint.unlockAccount(account);
        //TODO jakas obsluga wyjatkow?
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Account was unblocked.", null));
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class OwnAccountDetailsController implements Serializable {

    @Inject
    private AccountDetailsEndpointLocal accountDetailsEndpointLocal;
    @Inject
    private Conversation conversation;
    @Getter
    private AccountDTO account;

    public String selectOwnAccount() throws AppBaseException {
        conversation.begin();
        this.account = accountDetailsEndpointLocal.getOwnAccount();
        return "ownAccountDetails";
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
    public String getAccountDetailsConversationID(){
        return conversation.getId();
    }
}

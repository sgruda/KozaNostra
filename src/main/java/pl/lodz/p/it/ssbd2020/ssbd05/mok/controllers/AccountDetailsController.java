package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.Account;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
@RolesAllowed(value = "ADMIN")
public class AccountDetailsController implements Serializable {

    @Inject
    private Conversation conversation;
    @Getter
    private Account account; //docelowo AccountDTO

    public String selectAccount(Account account) {
        conversation.begin();
        this.account = account;
        return "accountDetails";
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.EditAccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Named
@ConversationScoped
public class ChangeOtherAccountPasswordController implements Serializable {

    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private String previousPassword;
    @Getter
    @Setter
    private AccountDTO accountDTO;


    public void init(String login) throws AppBaseException{
        conversation.begin();
        this.accountDTO=editAccountEndpointLocal.findByLogin(login);
        log.fine(this.accountDTO.getLogin() +" " + this.accountDTO.getFirstname());

    }

    @RolesAllowed(value = "ADMIN")
    public String redirectToChangePassword() {
        return "changePassword";
    }
}

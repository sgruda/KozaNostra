package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.EditAccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
    private String newPassword;
    @Getter
    @Setter
    private String newConfirmPassword;
    @Getter
    @Setter
    private AccountDTO accountDTO;


    public void init(String login) throws AppBaseException{
        if(!conversation.isTransient()) {
            conversation.end();
        }
        conversation.begin();
        this.accountDTO=editAccountEndpointLocal.findByLogin(login);
        log.severe("siema" + this.accountDTO.getLogin() + " " + this.accountDTO.getFirstname());

    }

    public void setPassword() throws AppBaseException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        AccountDTO accountDTO = editAccountEndpointLocal.findByLogin(context.getRemoteUser());

        try {
            editAccountEndpointLocal.changePassword(newPassword, accountDTO);
            ResourceBundles.emitMessage(null, "page.changepassword.message");
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (AccountPasswordAlreadyUsedException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }


    @RolesAllowed(value = "ADMIN")
    public String redirectToChangePassword() {
        return "changePassword";
    }
}

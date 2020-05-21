package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
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
    @Getter
    @Setter
    private String previousCid;


    public void init(String login, String cid) throws AppBaseException{
        if(!conversation.isTransient()) {
            conversation.end();
        }
        conversation.begin();
        this.accountDTO=editAccountEndpointLocal.findByLogin(login);
        this.previousCid=cid;
    }

    public void setPassword() throws AppBaseException {
        AccountDTO accountDTO = editAccountEndpointLocal.findByLogin(this.accountDTO.getLogin());
        try {
            editAccountEndpointLocal.changePassword(newPassword, accountDTO);
            ResourceBundles.emitMessage(null, "page.changepassword.message");
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (AccountPasswordAlreadyUsedException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }


    public String redirectToChangePassword() {
        return "changePassword";
    }

    public void goBack(){
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("/ssbd05/admin/accountDetails.xhtml?cid="+previousCid);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return;
    }

    public void validatePasswordMatch(FacesContext context, UIComponent component,
                                      Object value) {
        String confirmPassword = (String) value;
        UIInput passwordInput = (UIInput) component.findComponent("password");
        String password = (String) passwordInput.getLocalValue();

        if (password == null || !password.equals(confirmPassword)) {
            String key = "page.registration.account.password.matchfail";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundles.getTranslatedText(key),ResourceBundles.getTranslatedText(key));
            throw new ValidatorException(msg);
        }
    }

    public String getChangeOtherPasswdConversationID(){
        return conversation.getId();
    }
}

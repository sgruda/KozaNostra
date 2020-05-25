package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@ViewScoped
public class ChangeOtherAccountPasswordController implements Serializable {


    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;


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


    @PostConstruct
    public void init(){
        String selectedLogin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedLogin");
        try {
            this.accountDTO = editAccountEndpointLocal.findByLogin(selectedLogin);
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage());
        }
    }
    public void setPassword() {
        try {
            this.accountDTO.setPassword(newPassword);
            editAccountEndpointLocal.changeOtherAccountPassword(newPassword, accountDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.changepassword.message");
            goBack();
        } catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.changeotherpassword.optimisticlock");
            goBack();
        } catch (ValidationException e) {
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, appBaseException.getMessage());
        }
        return;
    }


    public String redirectToChangePassword() {
        return "changePassword";
    }

    public void goBack(){
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("/ssbd05/admin/accountDetails.xhtml");
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return;
    }
}

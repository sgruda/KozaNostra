package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@ViewScoped
public class ChangePasswordAccountController implements Serializable {

    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    @Getter
    @Setter
    private String password;
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
    public void init() {
        try {
            accountDTO = editAccountEndpointLocal.findByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        } catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ResourceBundles.getTranslatedText("error.default"));
        }
    }

    public void changePassword() {
        if(HashGenerator.sha256(password).equals(accountDTO.getPassword())){
            try{
                editAccountEndpointLocal.changePassword(newPassword,accountDTO);
                ResourceBundles.emitMessageWithFlash(null,"page.changepassword.message");
            }catch(AppOptimisticLockException ex){
                ResourceBundles.emitErrorMessageWithFlash(null,"error.account.optimisticlock");
                log.warning(ex.getClass().toString() + " " + ex.getMessage());
            }catch(AccountPasswordAlreadyUsedException ex){
                log.warning(ex.getClass().toString() + " " + ex.getMessage());
                ResourceBundles.emitErrorMessageWithFlash(null,ex.getMessage());
            }catch(AppBaseException ex){
                log.warning(ex.getClass().toString() + " " + ex.getMessage());
                ResourceBundles.emitErrorMessageWithFlash(null,ex.getMessage());
            }
        }else{
            ResourceBundles.emitErrorMessageWithFlash(null,"page.changepassword.wrong.current.password");
        }
    }

    public String goBack() {
        return "accountDetails";
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ChangePasswordAccountController {

    @Inject
    private EditAccountEndpointLocal editAccountEndpoint;

    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String newPassword;
    @Getter
    @Setter
    private String newConfirmPassword;

    public void setPassword() throws AppBaseException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        AccountDTO accountDTO = editAccountEndpoint.findByLogin(context.getRemoteUser());
        if(HashGenerator.sha256(password).equals(accountDTO.getPassword())){
            try{
                editAccountEndpoint.changePassword(newPassword,accountDTO);
                ResourceBundles.emitMessage(null,"page.changepassword.message");
            }catch(AppOptimisticLockException ex){
                ResourceBundles.emitErrorMessage(null,ex.getMessage());
            }catch(AccountPasswordAlreadyUsedException ex){
                ResourceBundles.emitErrorMessage(null,ex.getMessage());
            }
        }else{
            ResourceBundles.emitErrorMessage(null,"page.changepassword.wrong.current.password");
        }
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Data;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.EmailAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.LoginAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.RegisterAccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Data
@RequestScoped
public class RegistrationController implements Serializable {

    @Inject
    RegisterAccountEndpoint registerAccountEndpoint;

    private String login;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String emailAddress;


    public String register() throws AppBaseException {
        try {
            if (password.equals(confirmPassword)) {
                AccountDTO account = new AccountDTO();
                account.setLogin(this.getLogin());
                account.setPassword(password);
                account.setFirstname(this.getFirstname());
                account.setLastname(this.getLastname());
                account.setEmail(this.getEmailAddress());
                account.getAccessLevelCollection().add("CLIENT");
                account.setActive(true);
                account.setConfirmed(false);

                this.registerAccountEndpoint.addNewAccount(account);

                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                ResourceBundles.emitMessage(null,"page.registration.account.created");
                clear();
            }
        } catch (LoginAlreadyExistsException ex) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            ResourceBundles.emitErrorMessage(null,ex.getMessage());
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, "Login", ex);
        }catch (EmailAlreadyExistsException ex){
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            ResourceBundles.emitErrorMessage(null,ex.getMessage());
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, "Email", ex);
        }
        return "home";
    }

    public void clear() {
        this.setLogin("");
        this.setPassword("");
        this.setConfirmPassword("");
        this.setEmailAddress("");
        this.setFirstname("");
        this.setLastname("");
    }
}

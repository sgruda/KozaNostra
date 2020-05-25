package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Data;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.PropertiesLoadingException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.EmailAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.LoginAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.RegisterAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;

@Log
@Named
@Data
@RequestScoped
public class RegistrationController implements Serializable {

    @Inject
    private RegisterAccountEndpointLocal registerAccountEndpointLocal;

    private String login;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String emailAddress;


    public String register() {
        try {
            if (password.equals(confirmPassword)) {
                AccountDTO account = new AccountDTO();
                account.setLogin(this.getLogin());
                account.setPassword(password);
                account.setFirstname(this.getFirstname());
                account.setLastname(this.getLastname());
                account.setEmail(this.getEmailAddress());
                Properties properties = ResourceBundles.loadProperties("config.user_roles.properties");
                account.getAccessLevelCollection().add(properties.getProperty("roleClient"));
                account.setActive(true);
                account.setConfirmed(false);

                this.registerAccountEndpointLocal.addNewAccount(account);

                ResourceBundles.emitMessageWithFlash(null,"page.registration.account.created");
                return "home";
            }
        } catch (LoginAlreadyExistsException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.log(Level.SEVERE, "Login", ex);
        }catch (EmailAlreadyExistsException ex){
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.log(Level.SEVERE, "Email", ex);
        } catch (ExceededTransactionRetriesException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.log(Level.SEVERE, ex.getClass().toString(), ex);
        } catch(PropertiesLoadingException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ResourceBundles.getTranslatedText("error.simple"));
            log.log(Level.SEVERE, ex.getClass().toString(), ex);
        } catch (ValidationException e) {
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        return "register";
    }
}

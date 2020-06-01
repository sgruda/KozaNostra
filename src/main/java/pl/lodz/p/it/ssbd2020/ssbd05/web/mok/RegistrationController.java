package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

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
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * The type Registration controller.
 */
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


    /**
     * Register string.
     *
     * @return the string
     */
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
            log.severe(ex.getMessage() + " Login, " + LocalDateTime.now());
        }catch (EmailAlreadyExistsException ex){
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.severe(ex.getMessage() + " Email, " + LocalDateTime.now());
        } catch (ExceededTransactionRetriesException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        } catch(PropertiesLoadingException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ResourceBundles.getTranslatedText("error.default"));
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        } catch (ValidationException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        return "register";
    }
}

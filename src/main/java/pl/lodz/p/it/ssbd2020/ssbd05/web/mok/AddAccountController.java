package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.EmailAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.LoginAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AddAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@Getter
@Setter
@Named
@RequestScoped
public class AddAccountController {

    @Inject
    private AddAccountEndpointLocal addAccountEndpoint;

    private String login;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private List<String> accessLevels = new ArrayList<>();
    private boolean active;

    public String addAccount() {
        AccountDTO account = new AccountDTO();
        account.setLogin(login);
        account.setPassword(password);
        account.setFirstname(firstname);
        account.setLastname(lastname);
        account.setEmail(emailAddress);
        account.setActive(active);
        account.setAccessLevelCollection(accessLevels);
        account.setConfirmed(true);

        try {
            addAccountEndpoint.addAccount(account);
            ResourceBundles.emitMessageWithFlash(null,"page.registration.account.created");
        } catch (LoginAlreadyExistsException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.log(Level.SEVERE, "Login", ex);
        } catch (EmailAlreadyExistsException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.log(Level.SEVERE, "Email", ex);
        } catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.log(Level.SEVERE, ex.getClass().toString(), ex);
        }
        return goBack();
    }

    public String goBack() {
        return "home";
    }
}

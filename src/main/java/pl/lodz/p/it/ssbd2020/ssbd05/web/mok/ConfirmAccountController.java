package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ConfirmAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@RequestScoped
public class ConfirmAccountController implements Serializable {

    @Inject
    private ConfirmAccountEndpointLocal confirmAccountEndpointLocal;

    @Getter
    private AccountDTO account;
    @Getter
    @Setter
    private String url = "";
    private String token = "";

    public String confirmAccount() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        if (url.contains("token="))
            token = url.substring(url.indexOf("token=") + 6);

        try {
            account = confirmAccountEndpointLocal.getAccountByToken(token);
            if (account.getVeryficationToken().equals(token)) {
                confirmAccountEndpointLocal.confirmAccount();
                ResourceBundles.emitMessage(null, "messages.account.confirmed");
            } else ResourceBundles.emitErrorMessage(null, "error.default");
        } catch (AccountAlreadyConfirmedException e) {
            ResourceBundles.emitErrorMessage(null, "error.account.confirmed");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
        return "home";
    }
}

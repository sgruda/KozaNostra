package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ConfirmAccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class ConfirmAccountController implements Serializable {

    @Inject
    private ConfirmAccountEndpoint confirmAccountEndpoint;

    @Getter
    private AccountDTO account;
    @Getter
    @Setter
    private String url = "";
    private String token = "";

    public String confirmAccount() throws AppBaseException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        if (url.contains("token="))
            token = url.substring(url.indexOf("token=") + 6);
        account = confirmAccountEndpoint.getAccountByToken(token);
        if (account.getVeryficationToken().equals(token)) {
            try {
                confirmAccountEndpoint.confirmAccount();
                ResourceBundles.emitMessage(null, "messages.account.confirmed");
            } catch (AccountAlreadyConfirmedException e) {
                ResourceBundles.emitErrorMessage(null, "error.account.confirmed");
            } catch (AppBaseException e) {
                ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            }
        } else ResourceBundles.emitErrorMessage(null, "error.default");
        return "home";
    }
}

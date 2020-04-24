package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ConfirmAccountEndpoint;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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
    private String parameters = "";
    private String token = "";
    private String login = "";

    public String confirmAccount() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(url.contains("token="))
            parameters = url.substring(url.indexOf("token=") + 6);
        if(url.contains("&login=")) {
            token = parameters.substring(0, parameters.indexOf("&login="));
            login = parameters.substring(parameters.indexOf("&login=") + 7);
        }
        account = confirmAccountEndpoint.getAccountByLogin(login);
        if (account.getVeryficationToken().equals(token)) {
            confirmAccountEndpoint.confirmAccount();
            facesContext.addMessage(null, new FacesMessage("Account was successfully confirmed!"));
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
        } else facesContext.addMessage(null, new FacesMessage("Account could not be confirmed!"));
        return "home";
    }
}

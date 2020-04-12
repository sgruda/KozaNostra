package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ConfirmAccountDTOEndpoint;

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
    private ConfirmAccountDTOEndpoint confirmAccountDTOEndpoint;

    @Getter
    private AccountDTO accountDTO;
    @Getter
    @Setter
    private String url = "";

    public String confirmAccount() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String parameters = url.substring(url.indexOf("token=") + 6);
        String token = parameters.substring(0, parameters.indexOf("&login="));
        String login = parameters.substring(parameters.indexOf("&login=") + 7);
        accountDTO = confirmAccountDTOEndpoint.getAccountByLogin(login);
        if (accountDTO.getVeryficationToken().equals(token)) {
            confirmAccountDTOEndpoint.confirmAccount(accountDTO);
            facesContext.addMessage(null, new FacesMessage("Account was successfully confirmed!"));
        } else facesContext.addMessage(null, new FacesMessage("Account with that username could not be confirmed!"));
        return "home";
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ConfirmAccountDTOEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class ConfirmAccountController implements Serializable {

    @Inject
    private ConfirmAccountDTOEndpoint confirmAccountDTOEndpoint;

    @Getter
    private Account accountDTO;
    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String url = "";

    public void confirmAccount() {
        accountDTO = confirmAccountDTOEndpoint.getAccountByLogin(login);
        String token = url.substring(url.indexOf("token=") + 6);
        if(!accountDTO.isConfirmed()) {
            if(accountDTO.getVeryficationToken().equals(token)) {
                accountDTO.setConfirmed(true);
                confirmAccountDTOEndpoint.confirmAccount(accountDTO);
            }
        }
    }
}

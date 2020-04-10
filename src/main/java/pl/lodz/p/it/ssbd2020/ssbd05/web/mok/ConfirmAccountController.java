package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ConfirmAccountDTOEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class ConfirmAccountController implements Serializable {

    @Inject
    private ConfirmAccountDTOEndpoint confirmAccountDTOEndpoint;

    @Getter
    private AccountDTO accountDTO;
    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String url;

    private Logger logger = Logger.getLogger((getClass().getName()));

    public void confirmAccount() {
        //accountDTO = confirmAccountDTOEndpoint.getAccountByLogin(login);
        logger.log(Level.INFO, "BBB" + url);
//        if(!accountDTO.isConfirmed()) {
//            if(accountDTO.getVeryficationToken().equals(token)) {
//                accountDTO.setConfirmed(true);
//                confirmAccountDTOEndpoint.confirmAccount(accountDTO);
//            }
//        }
    }
}

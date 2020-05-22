package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@Dependent
public class ActivationAccountController implements Serializable {
    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    private AccountDTO account;

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
        try {
            editAccountEndpointLocal.findByLogin(account.getLogin());
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ResourceBundles.getTranslatedText("error.default"));
        }
    }

    public void unlockAccount() throws AppBaseException {
        editAccountEndpointLocal.unlockAccount(account);
        ResourceBundles.emitMessage(null,"page.accountdetails.unlock");
    }

    public void blockAccount() throws AppBaseException {
        editAccountEndpointLocal.blockAccount(account);
        ResourceBundles.emitMessage(null,"page.accountdetails.blocked");
    }
}

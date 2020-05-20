package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Named
@RequestScoped
public class ActivationAccountController implements Serializable {
    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    public void unlockAccount(AccountDTO account) throws AppBaseException {
        editAccountEndpointLocal.findByLogin(account.getLogin());
        editAccountEndpointLocal.unlockAccount(account);
        ResourceBundles.emitMessage(null,"page.accountdetails.unlock");
    }

    public void blockAccount(AccountDTO account) throws AppBaseException {
        editAccountEndpointLocal.findByLogin(account.getLogin());
        editAccountEndpointLocal.blockAccount(account);
        ResourceBundles.emitMessage(null,"page.accountdetails.blocked");
    }
}

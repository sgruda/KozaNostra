package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;

import javax.annotation.security.RolesAllowed;
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

    public void unlockAccount(AccountDTO account) {
        try {
            editAccountEndpointLocal.findByLogin(account.getLogin());
            editAccountEndpointLocal.unlockAccount(account);
        } catch (ExceededTransactionRetriesException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, e.getMessage());
        } catch (AppOptimisticLockException ex) {
            log.warning(ex.getClass().toString() + " " + ex.getMessage());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (AppBaseException ex) {
            log.warning(ex.getClass().toString() + " " + ex.getMessage());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
        ResourceBundles.emitMessage(null,"page.accountdetails.unlock");
    }

    public void blockAccount(AccountDTO account) {
        try {
            editAccountEndpointLocal.findByLogin(account.getLogin());
            editAccountEndpointLocal.blockAccount(account);
        } catch (ExceededTransactionRetriesException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
           ResourceBundles.emitErrorMessage(null, e.getMessage());
        }catch (AppOptimisticLockException ex) {
            log.warning(ex.getClass().toString() + " " + ex.getMessage());
        }catch (AppBaseException ex) {
            log.warning(ex.getClass().toString() + " " + ex.getMessage());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
        ResourceBundles.emitMessage(null,"page.accountdetails.blocked");
    }
}

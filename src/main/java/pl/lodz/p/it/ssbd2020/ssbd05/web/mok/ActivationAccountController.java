package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.TransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.EditAccountEndpoint;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class ActivationAccountController implements Serializable {
    @Inject
    private EditAccountEndpoint editAccountEndpoint;

    @RolesAllowed(value = "ADMIN")
    public void unlockAccount(AccountDTO account) {
        try {
            editAccountEndpoint.unlockAccount(account);
        } catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
        ResourceBundles.emitMessage(null,"page.accountdetails.unlock");
    }
    @RolesAllowed(value = "ADMIN")
    public void blockAccount(AccountDTO account) {
        try {
            editAccountEndpoint.blockAccount(account);
        } catch (ExceededTransactionRetriesException e) {
           ResourceBundles.emitErrorMessage(null, e.getMessage());
        }catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
        ResourceBundles.emitMessage(null,"page.accountdetails.blocked");
    }
}

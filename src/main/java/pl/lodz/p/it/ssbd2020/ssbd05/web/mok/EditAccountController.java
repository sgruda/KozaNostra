package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.EditAccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import pl.lodz.p.it.ssbd2020.ssbd05.web.auth.RegistrationController;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Named
@RequestScoped
public class EditAccountController {
    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    @Getter
    @Setter
    private AccountDTO accountDTO;

    public void editAccount() throws AppBaseException {
        try {
            editAccountEndpointLocal.editOwnAccount(accountDTO);
            ResourceBundles.emitMessage(null,"page.edit.ownacoount.message");
        } catch (AppOptimisticLockException ex) {
            log.error(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (ExceededTransactionRetriesException ex) {
            log.error(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (DatabaseQueryException ex) {
            log.error(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (DatabaseConnectionException ex){
            log.error(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }

    }

    @PostConstruct
    public void init() {
        try {
            accountDTO = editAccountEndpointLocal.findByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        } catch (AppBaseException ex) {
            log.error(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ResourceBundles.getTranslatedText("error.default"));
        }
    }


}

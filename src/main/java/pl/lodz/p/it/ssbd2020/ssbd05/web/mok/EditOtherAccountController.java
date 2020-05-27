package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@ViewScoped
public class EditOtherAccountController implements Serializable {

    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    @Getter
    @Setter
    private AccountDTO accountDTO;

    @PostConstruct
    public void init() {
        String selectedLogin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedLogin");
        try {
            this.accountDTO = editAccountEndpointLocal.findByLogin(selectedLogin);
        }
        catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (ExceededTransactionRetriesException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (DatabaseQueryException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (DatabaseConnectionException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }

    public void editAccount() {
        try {
            editAccountEndpointLocal.editAccount(accountDTO);
            ResourceBundles.emitMessageWithFlash(null,"page.edit.account.message");
        } catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.account.optimisticlock");
        } catch (ExceededTransactionRetriesException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
        } catch (DatabaseQueryException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
        } catch (DatabaseConnectionException ex){
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
        } catch (ValidationException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public String goBack() {
        return "accountDetails";
    }
}
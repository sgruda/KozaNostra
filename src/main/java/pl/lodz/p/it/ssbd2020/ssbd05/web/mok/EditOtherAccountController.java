package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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

//    @Inject
//    private Conversation conversation;

    @Getter
    @Setter
    private AccountDTO accountDTO;

    @PostConstruct
    public void selectAccount() {
//        this.accountDTO = accountDTO;
        String selectedLogin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedLogin");
        try{
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
        }catch (DatabaseConnectionException ex){
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ResourceBundles.getTranslatedText("error.default"));
        }
    }

//    public String getEditAccountConversationID(){
//        return conversation.getId();
//    }

    public void editAccount() throws AppBaseException {
        try {
            editAccountEndpointLocal.editAccount(accountDTO);
            ResourceBundles.emitMessage(null,"page.edit.account.message");
        } catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (ExceededTransactionRetriesException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (DatabaseQueryException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (DatabaseConnectionException ex){
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }

    }
}
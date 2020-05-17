package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.AccountDetailsEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@ConversationScoped
public class AccountDetailsController implements Serializable {

    @Inject
    private AccountDetailsEndpoint accountDetailsEndpoint;
    @Inject
    private Conversation conversation;
    @Getter
    private AccountDTO account;
    @Inject
    private ActivationAccountController activationAccountController;

    public String selectAccount(AccountDTO accountDTO) {
        conversation.begin();
        this.account = accountDetailsEndpoint.getAccount(accountDTO.getLogin());
        return "accountDetails";
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
    
    public String getAccountDetailsConversationID(){
        return conversation.getId();
    }

    public void unlockAccount() throws AppBaseException {
        try{
            activationAccountController.unlockAccount(account);
            refresh();
        }catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }


    }

    public void blockAccount() {
        try{
            activationAccountController.blockAccount(account);
            refresh();
        }catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
        } catch (AppOptimisticLockException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }


    }
    public void refresh() {
        this.account = accountDetailsEndpoint.getAccount(account.getLogin());
    }
}

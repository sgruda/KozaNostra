package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountBlockedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
//@RolesAllowed(value = "ADMIN")    TODO Kwesita jest, tego endpointa moze uzywac jeszcze klient, takze chyba jedna adntoacja nie wystarczy
public class EditAccountEndpoint implements Serializable {
    @Inject
    private AccountManager accountManager;
    private Account account;

    //Ustawilem tego cczego potrzebowalem do odblokowywania, przy edycji bedzie trzeba dodac reszte
    public AccountDTO findByLogin(String username) {
        account = accountManager.findByLogin(username);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setLogin(account.getLogin());
        accountDTO.setActive(account.isActive());
        accountDTO.setFailedAuthCounter(account.getFailedAuthCounter());
        return accountDTO;
    }

    public void edit(AccountDTO accountDTO) throws AppBaseException {
        account.setFailedAuthCounter(accountDTO.getFailedAuthCounter());
        account.setActive(accountDTO.isActive());
        accountManager.edit(account);
    }

    public void blockAccount(AccountDTO accountDTO) throws AppBaseException {
        account = accountManager.findByLogin(accountDTO.getLogin());
        int callCounter = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
        do {
            accountManager.blockAccount(account);
            callCounter--;
        } while (accountManager.isLastTransactionRollback() && callCounter > 0);
        if (callCounter == 0) {
            throw new ExceededTransactionRetriesException();
        }
    }

    public void unlockAccount(AccountDTO accountDTO) throws AppBaseException {
        account = accountManager.findByLogin(accountDTO.getLogin());
        int callCounter = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
        do {
            accountManager.unlockAccount(account);
            callCounter--;
        } while (accountManager.isLastTransactionRollback() && callCounter > 0);
        if (callCounter == 0) {
            throw new ExceededTransactionRetriesException();
        }
    }
}

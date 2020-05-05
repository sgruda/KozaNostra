package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.ejb.LocalBean;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import java.util.Collection;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.StringUtils.collectionContainsIgnoreCase;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
//@RolesAllowed(value = "ADMIN")    TODO Kwesita jest, tego endpointa moze uzywac jeszcze klient, takze chyba jedna adntoacja nie wystarczy
public class EditAccountEndpoint implements Serializable {
    @Inject
    private AccountManager accountManager;
    private Account account;

    //Ustawilem tego cczego potrzebowalem do odblokowywania, przy edycji bedzie trzeba dodac reszte
//    public AccountDTO findByLogin(String username) {
//        Account account = accountManager.findByLogin(username);
//        return AccountMapper.INSTANCE.toAccountDTO(account);
//    }

    public void edit(AccountDTO accountDTO) throws AppBaseException {
        Account account = accountManager.findByLogin(accountDTO.getLogin());
        Collection<AccessLevel> accessLevelCollection = account.getAccessLevelCollection();
        Collection<String> accessLevelStringCollection = accountDTO.getAccessLevelCollection();
        AccountMapper.INSTANCE.updateAccountFromDTO(accountDTO, account);
        for (AccessLevel accessLevel : accessLevelCollection) {
            if (accessLevel instanceof Admin) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, "admin"));
            } else if (accessLevel instanceof Manager) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, "manager"));
            } else if (accessLevel instanceof Client) {
                accessLevel.setActive(collectionContainsIgnoreCase(accessLevelStringCollection, "client"));
            }
        }
        account.setAccessLevelCollection(accessLevelCollection);
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

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.PreviousPassword;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.TransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;

import javax.ejb.*;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;

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


    public AccountDTO findByLogin(String username) {
        account = accountManager.findByLogin(username);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setLogin(account.getLogin());
        accountDTO.setActive(account.isActive());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setFailedAuthCounter(account.getFailedAuthCounter());
        return accountDTO;
    }

    public void changePassword(String newPassword, AccountDTO accountDTO) throws AppBaseException {
        account = accountManager.findByLogin(accountDTO.getLogin());
        boolean alreadyUsed = false;
        for (PreviousPassword psw: account.getPreviousPasswordCollection()){
            if(psw.getPassword().equals(HashGenerator.sha256(newPassword))){
                alreadyUsed = true;
                throw new AccountPasswordAlreadyUsedException();
            }
        }
        if(alreadyUsed == false){
            account.setPassword(HashGenerator.sha256(newPassword));
            PreviousPassword previousPassword = new PreviousPassword();
            previousPassword.setAccount(account);
            previousPassword.setPassword(HashGenerator.sha256(newPassword));
            account.getPreviousPasswordCollection().add(previousPassword);
            int callCounter = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
            do {
                accountManager.edit(account);
                callCounter--;
            } while (accountManager.isLastTransactionRollback() && callCounter > 0);
            if (callCounter == 0) {
                throw new ExceededTransactionRetriesException();
            }
        }
    }


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
            try{
                accountManager.blockAccount(account);
                callCounter--;
            }catch (EJBTransactionRolledbackException ex){
                throw new TransactionRolledbackException();
            }
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

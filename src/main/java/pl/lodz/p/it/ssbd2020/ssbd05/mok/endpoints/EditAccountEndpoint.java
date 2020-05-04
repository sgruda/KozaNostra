package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.PreviousPassword;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountBlockedException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
        accountDTO.setPassword(account.getPassword());
        accountDTO.setFailedAuthCounter(account.getFailedAuthCounter());
        return accountDTO;
    }

    public void edit(AccountDTO accountDTO) throws AppBaseException {
        account.setFailedAuthCounter(accountDTO.getFailedAuthCounter());
        account.setActive(accountDTO.isActive());
        accountManager.edit(account);
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
            accountManager.edit(account);
        }
    }

    public void blockAccount(AccountDTO accountDTO) throws AppBaseException {
        account = accountManager.findByLogin(accountDTO.getLogin());
        accountManager.blockAccount(account);
    }

    public void unlockAccount(AccountDTO accountDTO) throws AppBaseException{
        account = accountManager.findByLogin(accountDTO.getLogin());
        accountManager.unlockAccount(account);
    }
}

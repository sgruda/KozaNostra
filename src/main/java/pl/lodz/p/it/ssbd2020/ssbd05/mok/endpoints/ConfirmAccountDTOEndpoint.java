package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ConfirmAccountDTOEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;
    private Account account;

    public AccountDTO getAccountByLogin(String login) {
        account = accountManager.findByLogin(login);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setLogin(account.getLogin());
        accountDTO.setVeryficationToken(account.getVeryficationToken());
        return accountDTO;
    }

    public void confirmAccount(AccountDTO accountDTO) {
        account = accountManager.findById(accountDTO.getId());
        if(!account.isConfirmed()) {
            account.setConfirmed(true);
            accountManager.edit(account);
        }
    }
}

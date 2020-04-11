package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ConfirmAccountDTOEndpoint implements Serializable {

    @Inject
    private AccountFacade accountFacade;
    private Account account;

    public AccountDTO getAccountByLogin(String login) {
        account = accountFacade.findByLogin(login);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setLogin(account.getLogin());
        accountDTO.setVeryficationToken(account.getVeryficationToken());
        return accountDTO;
    }

    public void confirmAccount(AccountDTO accountDTO) {
        account = accountFacade.find(accountDTO.getId());
        if(!account.isConfirmed()) {
            account.setConfirmed(true);
            accountFacade.edit(account);
        }
    }
}

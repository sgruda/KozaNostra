package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateless
public class LastLoginEndpoint implements Serializable {

    @Inject
    private AccountFacade accountFacade;

    public AccountDTO findByLogin(String username) {
        AccountDTO accountDTO = new AccountDTO();
        Account account = accountFacade.findByLogin(username);
        accountDTO.setLogin(account.getLogin());
        accountDTO.setActive(account.isActive());
        accountDTO.setConfirmed(account.isConfirmed());
        accountDTO.setFailedAuthCounter(account.getFailedAuthCounter());
        accountDTO.setLastSuccessfulAuth(account.getLastSuccessfulAuth());
        accountDTO.setLastFailedAuth(account.getLastFailedAuth());
        return accountDTO;
    }

    public void edit(AccountDTO accountDTO) {
        Account account = accountFacade.findByLogin(accountDTO.getLogin());
        account.setFailedAuthCounter(accountDTO.getFailedAuthCounter());
        account.setLastSuccessfulAuth(accountDTO.getLastSuccessfulAuth());
        account.setLastFailedAuth(accountDTO.getLastFailedAuth());
        accountFacade.edit(account);
    }
}

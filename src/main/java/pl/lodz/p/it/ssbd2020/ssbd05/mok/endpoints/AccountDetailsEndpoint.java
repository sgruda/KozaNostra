package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.stream.Collectors;

@Named
@Stateless
public class AccountDetailsEndpoint implements Serializable {

    @Inject
    private AccountFacade accountFacade;

    public AccountDTO getAccount(Long id) {
        AccountDTO accountDTO = new AccountDTO();
        Account account = accountFacade.find(id).get();
        accountDTO.setId(account.getId());
        accountDTO.setLogin(account.getLogin());
        accountDTO.setFirstname(account.getFirstname());
        accountDTO.setLastname(account.getLastname());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setActive(account.isActive());
        accountDTO.setConfirmed(account.isConfirmed());
        accountDTO.setAccessLevelCollection(
                account.getAccessLevelCollection()
                .stream()
                .filter(AccessLevel::getActive)
                .map(AccessLevel::getAccessLevel)
                .collect(Collectors.toList())
        );
        accountDTO.setLastSuccessfulAuth(account.getLastSuccessfulAuth());
        accountDTO.setLastFailedAuth(account.getLastFailedAuth());
        accountDTO.setLastAuthIp(account.getLastAuthIp());
        return accountDTO;
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.stream.Collectors;

@Named
@Stateful
@RolesAllowed(value = "ADMIN")
public class ListAccountsEndpoint {

    @Inject
    private AccountManager accountManager;

    private AccountDTO accountToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setLogin(account.getLogin());
        accountDTO.setFirstname(account.getFirstname());
        accountDTO.setLastname(account.getLastname());
        accountDTO.setEmail(account.getEmail());
        return accountDTO;
    }

    public Collection<AccountDTO> getAllAccounts() {
        return accountManager.getAllAccounts()
                .stream()
                .map(this::accountToDTO)
                .collect(Collectors.toList());
    }
}

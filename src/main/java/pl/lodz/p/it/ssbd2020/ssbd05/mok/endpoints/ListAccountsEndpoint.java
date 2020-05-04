package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Named
@Stateful
@RolesAllowed(value = "ADMIN")
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class ListAccountsEndpoint {

    @Inject
    private AccountManager accountManager;

    private AccountDTO accountToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setLogin(account.getLogin());
        accountDTO.setFirstname(account.getFirstname());
        accountDTO.setLastname(account.getLastname());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setLastSuccessfulAuth(account.getLastSuccessfulAuth());
        accountDTO.setLastAuthIp(account.getLastAuthIp());
        return accountDTO;
    }

    public List<AccountDTO> getAllAccounts() {
        return accountManager.getAllAccounts()
                .stream()
                .map(this::accountToDTO)
                .collect(Collectors.toList());
    }

    public List<AccountDTO> filterAccounts (String accountFilter){
        return accountManager.filterAccounts(accountFilter)
                .stream()
                .map(this::accountToDTO)
                .collect(Collectors.toList());
    }

}

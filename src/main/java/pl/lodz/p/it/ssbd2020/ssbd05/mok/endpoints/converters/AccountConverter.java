package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.converters;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;

import java.util.stream.Collectors;

public class AccountConverter {

    public static AccountDTO accountToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setLogin(account.getLogin());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setActive(account.isActive());
        accountDTO.setConfirmed(account.isConfirmed());
        accountDTO.setAccessLevelCollection(
                account
                .getAccessLevelCollection()
                .stream()
                .map(AccessLevel::getAccessLevel)
                .collect(Collectors.toList())
        );
        accountDTO.setFirstname(account.getFirstname());
        accountDTO.setLastname(account.getLastname());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setLastSuccessfulAuth(account.getLastSuccessfulAuth());
        accountDTO.setLastFailedAuth(account.getLastFailedAuth());
        accountDTO.setLastAuthIp(account.getLastAuthIp());
        accountDTO.setVeryficationToken(account.getVeryficationToken());
        return accountDTO;
    }

    public static Account DTOtoAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setLogin(accountDTO.getLogin());
        account.setPassword(accountDTO.getPassword());
        account.setActive(accountDTO.isActive());
        account.setConfirmed(accountDTO.isConfirmed());
//        account.setAccessLevelCollection();
        account.setFirstname(accountDTO.getFirstname());
        account.setLastname(accountDTO.getLastname());
        account.setEmail(accountDTO.getEmail());
        account.setLastSuccessfulAuth(accountDTO.getLastSuccessfulAuth());
        account.setLastFailedAuth(accountDTO.getLastFailedAuth());
        account.setLastAuthIp(accountDTO.getLastAuthIp());
        account.setVeryficationToken(account.getVeryficationToken());
        return account;
    }
}

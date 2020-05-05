package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class AccountDetailsEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;

    public AccountDTO getAccount(String login) {
        Account account = accountManager.findByLogin(login);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Local
public class AccountDetailsEndpoint implements Serializable, AccountDetailsEndpointLocal {

    @Inject
    private AccountManager accountManager;
    @Resource
    private SessionContext sessionContext;

    private Account account;

    @RolesAllowed("getOtherAccount")
    public AccountDTO getAccount(String login) {
        this.account = accountManager.findByLogin(login);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @RolesAllowed("getOwnAccount")
    public AccountDTO getOwnAccount() {
        String login = sessionContext.getCallerPrincipal().getName();
        this.account = accountManager.findByLogin(login);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }
}

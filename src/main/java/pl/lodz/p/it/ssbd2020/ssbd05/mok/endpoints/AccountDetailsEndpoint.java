package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.TrackerInterceptor;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class AccountDetailsEndpoint implements Serializable {

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

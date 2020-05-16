package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collection;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class LastLoginEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;
    private Account account;

    @PermitAll
    public String getFailedAttemptNumberFromProperties() throws AppBaseException {
        return ResourceBundles.loadProperties("config.login.properties").getProperty("blockingAccountAfterFailedAttemptNumber");
    }

    @PermitAll
    public AccountDTO findByLogin(String username) {
        Account account = accountManager.findByLogin(username);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @PermitAll
    public void edit(AccountDTO accountDTO) throws AppBaseException {
        this.account = accountManager.findByLogin(accountDTO.getLogin());
        Collection<AccessLevel> accessLevelCollection = account.getAccessLevelCollection();
        AccountMapper.INSTANCE.updateAccountFromDTO(accountDTO, account);
        account.setAccessLevelCollection(accessLevelCollection);
        accountManager.edit(account);
    }
}

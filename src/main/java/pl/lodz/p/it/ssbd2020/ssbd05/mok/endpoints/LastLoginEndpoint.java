package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.LastLoginEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class LastLoginEndpoint implements Serializable, LastLoginEndpointLocal {

    @Inject
    private AccountManager accountManager;
    private Account account;

    @Override
    @PermitAll
    public String getFailedAttemptNumberFromProperties() throws AppBaseException {
        return ResourceBundles.loadProperties("config.login.properties").getProperty("blockingAccountAfterFailedAttemptNumber");
    }

    @Override
    @PermitAll
    public AccountDTO findByLogin(String username) throws AppBaseException{
        account = accountManager.findByLogin(username);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @Override
    @PermitAll
    public void edit(AccountDTO accountDTO) throws AppBaseException {
        this.account = accountManager.findByLogin(accountDTO.getLogin());
        Collection<AccessLevel> accessLevelCollection = account.getAccessLevelCollection();
        AccountMapper.INSTANCE.updateAccountFromDTO(accountDTO, account);
        account.setAccessLevelCollection(accessLevelCollection);
        accountManager.edit(account);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class LastLoginEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;
    private Account account;

    public String getFailedAttemptNumberFromProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.login.properties");
        Properties properties = new Properties();
        try {
            if(inputStream != null)
                properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("blockingAccountAfterFailedAttemptNumber");
    }

    public AccountDTO findByLogin(String username) {
        Account account = accountManager.findByLogin(username);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    public void edit(AccountDTO accountDTO) throws AppBaseException {
        Account account = accountManager.findByLogin(accountDTO.getLogin());
        Collection<AccessLevel> accessLevelCollection = account.getAccessLevelCollection();
        AccountMapper.INSTANCE.updateAccountFromDTO(accountDTO, account);
        accountManager.edit(account);
    }
}

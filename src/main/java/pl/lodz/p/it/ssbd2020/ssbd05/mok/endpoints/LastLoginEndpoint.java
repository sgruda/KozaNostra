package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
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
import java.util.Properties;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class LastLoginEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;

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
        AccountDTO accountDTO = new AccountDTO();
        Account account = accountManager.findByLogin(username);
        accountDTO.setLogin(account.getLogin());
        accountDTO.setActive(account.isActive());
        accountDTO.setConfirmed(account.isConfirmed());
        accountDTO.setFailedAuthCounter(account.getFailedAuthCounter());
        accountDTO.setLastSuccessfulAuth(account.getLastSuccessfulAuth());
        accountDTO.setLastFailedAuth(account.getLastFailedAuth());
        return accountDTO;
    }

    public void edit(AccountDTO accountDTO) {
        Account account = accountManager.findByLogin(accountDTO.getLogin());
        account.setFailedAuthCounter(accountDTO.getFailedAuthCounter());
        account.setLastSuccessfulAuth(accountDTO.getLastSuccessfulAuth());
        account.setLastFailedAuth(accountDTO.getLastFailedAuth());
        account.setActive(accountDTO.isActive());
        accountManager.edit(account);
    }
}

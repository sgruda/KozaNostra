package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

@Named
@Stateless
public class LastLoginEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;

    public Properties getProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("login.properties");
        Properties properties = new Properties();
        try {
            if(inputStream != null)
                properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
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

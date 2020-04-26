package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

@Named
@ConversationScoped
public class LastLoginController implements Serializable {
    @Inject
    private Conversation conversation;
    private AccountDTO account;
    private Properties properties;

    public void startConversation(AccountDTO accountDTO, Properties properties) {
        conversation.begin();
        this.account = accountDTO;
        this.properties = properties;
    }
    public AccountDTO endConversation() {
        conversation.end();
        return this.account;
    }
    public void updateLastAuthIP() {
        account.setLastAuthIp(this.getIP());
    }
    public void updateLastSuccesfullAuthDate() {
        account.setLastSuccessfulAuth(Date.from(Instant.now()));
        account.setFailedAuthCounter(0);
    }
    public void updateLastFailedAuthDate() {
        account.setLastFailedAuth(Date.from(Instant.now()));
        account.setFailedAuthCounter(account.getFailedAuthCounter() + 1);
    }
    public void checkFailedAuthCounter() throws Exception {
        if(account.getFailedAuthCounter() >= Integer.parseInt(properties.getProperty("blockingAccountAfterFailedAttemptNumber"))) {
            account.setActive(false);
            throw new Exception("Account was blocked");
            //TODO daj tu nasz wyjatek
        }
    }
    private String getIP() {
        URL urlToCheckIpAmazonaws;
        String ipAddress = "";
        try {
            urlToCheckIpAmazonaws = new URL("http://checkip.amazonaws.com");
            try(BufferedReader in = new BufferedReader(new InputStreamReader(urlToCheckIpAmazonaws.openStream()))) {
                ipAddress = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.EditAccountEndpoint;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
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
    @Inject
    EditAccountEndpoint editAccountEndpoint;
    private AccountDTO account;
    private int blockingAccountAfterFailedAttemptNumber;

    public void startConversation(AccountDTO accountDTO, String blockingAccountAfterFailedAttemptNumber) {
        conversation.begin();
        this.account = accountDTO;
        this.blockingAccountAfterFailedAttemptNumber = Integer.parseInt(blockingAccountAfterFailedAttemptNumber);
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
        if(account.getFailedAuthCounter() >= this.blockingAccountAfterFailedAttemptNumber ) {
            editAccountEndpoint.blockAccount(account);
        }
    }
    private String getIP() {
        URL urlToCheckIpAmazonaws;
        String ipAddress = "";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.ip_url.properties");
        Properties properties = new Properties();
        try {
            if(inputStream != null)
                properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlToCheckIpAmazonaws = new URL(properties.getProperty("urlToCheckIpAmazonaws"));
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

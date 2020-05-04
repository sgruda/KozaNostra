package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountBlockedException;
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
    private AccountDTO accountDTO;
    private int blockingAccountAfterFailedAttemptNumber;

    public void startConversation(AccountDTO accountDTO, String blockingAccountAfterFailedAttemptNumber) {
        conversation.begin();
        this.accountDTO = accountDTO;
        this.blockingAccountAfterFailedAttemptNumber = Integer.parseInt(blockingAccountAfterFailedAttemptNumber);
    }
    public AccountDTO endConversation() {
        conversation.end();
        return this.accountDTO;
    }
    public void updateLastAuthIP() {
        accountDTO.setLastAuthIp(this.getIP());
    }
    public void updateLastSuccesfullAuthDate() {
        accountDTO.setLastSuccessfulAuth(Date.from(Instant.now()));
        accountDTO.setFailedAuthCounter(0);
    }
    public void updateLastFailedAuthDate() {
        accountDTO.setLastFailedAuth(Date.from(Instant.now()));
        accountDTO.setFailedAuthCounter(accountDTO.getFailedAuthCounter() + 1);
    }
    public void checkFailedAuthCounter() throws AccountBlockedException {
        if(accountDTO.getFailedAuthCounter() >= this.blockingAccountAfterFailedAttemptNumber ) {
            accountDTO.setActive(false);
            try {
                editAccountEndpoint.blockAccount(accountDTO);
            } catch (AppBaseException e) {
                e.printStackTrace();
            }
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

package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;

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

@Named
@ConversationScoped
public class LastLoginController implements Serializable {
    @Inject
    private Conversation conversation;
    private Account account; //docelowo AccountDTO

    public void startConversation(Account account) {
        conversation.begin();
        this.account = account;
    }
    public Account endConversation() {
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

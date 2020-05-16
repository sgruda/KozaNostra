package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Properties;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.formatDate;

@Named
@ConversationScoped
public class LastLoginController implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private EditAccountEndpointLocal editAccountEndpoint;
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
        accountDTO.setLastSuccessfulAuth(formatDate(LocalDateTime.now()));
        accountDTO.setFailedAuthCounter(0);
    }
    public void updateLastFailedAuthDate() {
        accountDTO.setLastFailedAuth(formatDate(LocalDateTime.now()));
        accountDTO.setFailedAuthCounter(accountDTO.getFailedAuthCounter() + 1);
    }
    public void checkFailedAuthCounter() throws AppBaseException {
        if(accountDTO.getFailedAuthCounter() >= this.blockingAccountAfterFailedAttemptNumber ) {
            accountDTO.setActive(false);
            try {
                editAccountEndpoint.blockAccount(accountDTO);
                ResourceBundles.emitErrorMessageWithFlash(null, "page.login.account.lock");
            } catch (AppBaseException e) {
                e.printStackTrace();
            }
        }
    }

    public String getIP() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        final String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if(remoteAddr != null){
            return remoteAddr.replaceFirst(",.*","");
        }
        else{
            return  request.getRemoteAddr();
        }
    }
}

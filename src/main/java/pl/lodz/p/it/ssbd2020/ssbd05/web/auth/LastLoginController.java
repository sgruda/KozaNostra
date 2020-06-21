package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.formatDate;

/**
 * Kontroler odpowiedzalny za aktualizację danych o poprawnym oraz niepoprawnym uwierzytelnianiu
 */
@Log
@Named
@ConversationScoped
public class LastLoginController implements Serializable {
    @Inject
    private Conversation conversation;
    private AccountDTO accountDTO;
    private int blockingAccountAfterFailedAttemptNumber;

    /**
     * Metoda rozpoczynająca konwersacja w ConversationScope
     *
     * @param accountDTO  Obiekt DTO użytkownika, która próbuje się uwierzytelnić
     * @param blockingAccountAfterFailedAttemptNumber liczba prób, który zostały użytkownikowi przed zablokowaniem konta
     */
    public void startConversation(AccountDTO accountDTO, String blockingAccountAfterFailedAttemptNumber) {
        conversation.begin();
        this.accountDTO = accountDTO;
        this.blockingAccountAfterFailedAttemptNumber = Integer.parseInt(blockingAccountAfterFailedAttemptNumber);
    }

    /**
     * Kończy konwersajce w ConversationScope
     *
     * @return Obiekt DTO użytkownika, który próbuje się uwierzytelnić
     */
    public AccountDTO endConversation() {
        conversation.end();
        return this.accountDTO;
    }

    /**
     * Metoda aktualizująca adres logiczny IP, z którego odbyła się ostatnia próba uwierzytelnienia
     */
    public void updateLastAuthIP() {
        accountDTO.setLastAuthIp(this.getIP());
    }

    /**
     * Metoda aktualizująca datę ostatniej udanej próby uwierzytelnienia oraz zerująca licznik nieudanych uwierzytelnień
     */
    public void updateLastSuccesfullAuthDate() {
        accountDTO.setLastSuccessfulAuth(formatDate(LocalDateTime.now()));
        accountDTO.setFailedAuthCounter(0);
    }

    /**
     * Metoda aktualizująca datę ostatniej nieudanej próby uwierzytelnienia ora zwiększająca licznik nieudanych uwierzytelnień
     */
    public void updateLastFailedAuthDate() {
        accountDTO.setLastFailedAuth(formatDate(LocalDateTime.now()));
        accountDTO.setFailedAuthCounter(accountDTO.getFailedAuthCounter() + 1);
    }

    /**
     * Metoda sprawdzająca czy liczba nieudanych prób uwierzytleniania jest mniejsza lub równa od tej zdefiniowanej w deskryptorze
     */
    public void checkFailedAuthCounter() {
        if(accountDTO.getFailedAuthCounter() >= this.blockingAccountAfterFailedAttemptNumber ) {
            accountDTO.setActive(false);
            ResourceBundles.emitErrorMessageWithFlash(null, "page.login.account.lock");
        }
    }

    /**
     * Metoda pobierająca adres logiczny urządzenia, z którego użytkownik dokanł próby uwierzytleniania
     *
     * @return Adres logiczny IP
     */
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

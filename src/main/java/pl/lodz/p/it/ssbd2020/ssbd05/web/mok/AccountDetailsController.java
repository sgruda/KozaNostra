package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.AccountDTODetailsEndpoint;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ConversationScoped
@RolesAllowed(value = "ADMIN")
public class AccountDetailsController implements Serializable {

    @Inject
    private AccountDTODetailsEndpoint accountDTODetailsEndpoint;
    @Inject
    private Conversation conversation;
    @Getter
    private AccountDTO accountDTO;

    public String selectAccount(AccountDTO accountDTO) {
        conversation.begin();
        this.accountDTO = accountDTODetailsEndpoint.getAccountDTO(accountDTO.getId());
        return "accountDetails";
    }

    public String formatDate(Date date) {
        if(date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return simpleDateFormat.format(date);
    }

    public String goBack() {
        conversation.end();
        return "goBack";
    }
}

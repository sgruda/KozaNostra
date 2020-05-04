package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ListAccountsEndpoint;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@RolesAllowed(value = "ADMIN")
@Slf4j
public class ListAccountsController implements Serializable {

    @Inject
    private ListAccountsEndpoint listAccountsEndpoint;
    @Getter
    @Setter
    private List<AccountDTO> accounts;
    @Getter
    @Setter
    private String accountFilter;

    @PostConstruct
    public void init() {
        accounts = listAccountsEndpoint.getAllAccounts();
    }

    public void filterAccounts(){
        accounts = listAccountsEndpoint.filterAccounts(accountFilter);
        log.error(accounts.iterator().next().getFirstname() + " " + accounts.iterator().next().getLastname() + " " + accounts.iterator().next().getEmail() + " size " + accounts.size());
    }

    public String formatDate(Date date) {
        if(date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }
}

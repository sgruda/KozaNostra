package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ListAccountsEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Named
@ViewScoped
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
        try {
            accounts = (List<AccountDTO>) listAccountsEndpoint.getAllAccounts();
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public void filterAccounts(){
        try {
            accounts = (List<AccountDTO>) listAccountsEndpoint.filterAccounts(accountFilter);
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
}

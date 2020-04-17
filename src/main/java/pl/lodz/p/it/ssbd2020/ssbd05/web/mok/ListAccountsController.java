package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.ListAccountsEndpoint;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
@RolesAllowed(value = "ADMIN")
public class ListAccountsController implements Serializable {

    @Inject
    private ListAccountsEndpoint listAccountsEndpoint;
    @Getter
    private Collection<AccountDTO> accounts;

    @PostConstruct
    public void init() {
        accounts = listAccountsEndpoint.getAllAccounts();
    }
}

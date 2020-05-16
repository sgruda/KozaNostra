package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ListAccountsEndpointLocal;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListAccountsController implements Serializable {

    @Inject
    private ListAccountsEndpointLocal listAccountsEndpointLocal;
    @Getter
    @Setter
    private List<AccountDTO> accounts;
    @Getter
    @Setter
    private String accountFilter;

    @PostConstruct
    public void init() {
        accounts = (List<AccountDTO>) listAccountsEndpointLocal.getAllAccounts();
    }

    public void filterAccounts(){
        accounts = (List<AccountDTO>) listAccountsEndpointLocal.filterAccounts(accountFilter);
    }
}

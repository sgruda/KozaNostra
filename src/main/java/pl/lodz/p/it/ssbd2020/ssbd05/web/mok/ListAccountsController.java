package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@RolesAllowed(value = "ADMIN")
public class ListAccountsController implements Serializable {

    @EJB
    private AccountFacade accountFacade; //docelowo AccountService
    @Getter
    private List<Account> accounts;

    @PostConstruct
    public void init() {
        accounts = accountFacade.findAll();
    }
}

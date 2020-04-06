package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers;

import lombok.Data;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Data
@Named
@ViewScoped
@RolesAllowed(value = "ADMIN")
public class ListAccountsController implements Serializable {

    @EJB
    private AccountFacade accountFacade;
    private List<Account> accounts;

    @PostConstruct
    public void init() {
        accounts = accountFacade.findAll();
    }
}

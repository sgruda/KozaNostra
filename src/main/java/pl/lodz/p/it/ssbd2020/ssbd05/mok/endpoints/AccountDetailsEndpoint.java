package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class AccountDetailsEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;
    private Account account;

    @RolesAllowed("getOtherAccount")
    public AccountDTO getAccount(String login) {
        this.account = accountManager.findByLogin(login);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @RolesAllowed("getOwnAccount")
    public AccountDTO getOwnAccount() {
        String login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        this.account = accountManager.findByLogin(login);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }
}

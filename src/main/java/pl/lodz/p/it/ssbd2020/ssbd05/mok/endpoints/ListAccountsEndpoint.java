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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;

@Named
@Stateful
@RolesAllowed(value = "ADMIN")
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class ListAccountsEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;

    public Collection<AccountDTO> getAllAccounts() {
        return AccountMapper.INSTANCE.toAccountDTOCollection(accountManager.getAllAccounts());
    }

    public Collection<AccountDTO> filterAccounts (String accountFilter) {
        return AccountMapper.INSTANCE.toAccountDTOCollection(accountManager.filterAccounts(accountFilter));
    }
}

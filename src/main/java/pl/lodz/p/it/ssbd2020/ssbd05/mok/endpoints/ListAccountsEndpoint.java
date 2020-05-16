package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ListAccountsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;

@Stateful
@RolesAllowed(value = "ADMIN")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ListAccountsEndpoint implements Serializable, ListAccountsEndpointLocal {

    @Inject
    private AccountManager accountManager;

    @Override
    @RolesAllowed("listAccounts")
    public Collection<AccountDTO> getAllAccounts() {
        return AccountMapper.INSTANCE.toAccountDTOCollection(accountManager.getAllAccounts());
    }
    @Override
    @RolesAllowed("filterAccounts")
    public Collection<AccountDTO> filterAccounts(String accountFilter) {
        return AccountMapper.INSTANCE.toAccountDTOCollection(accountManager.filterAccounts(accountFilter));
    }
}

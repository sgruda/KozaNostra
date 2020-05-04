package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Named
@Stateful
@RolesAllowed(value = "ADMIN")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ListAccountsEndpoint {

    @Inject
    private AccountManager accountManager;

    public Collection<AccountDTO> getAllAccounts() {
        return AccountMapper.INSTANCE.toAccountDTOCollection(accountManager.getAllAccounts());
    }
}

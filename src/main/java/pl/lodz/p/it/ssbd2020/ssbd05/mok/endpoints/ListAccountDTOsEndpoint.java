package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.converters.AccountConverter;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.stream.Collectors;

@Named
@Stateless
@RolesAllowed(value = "ADMIN")
public class ListAccountDTOsEndpoint {

    @Inject
    private AccountManager accountManager;

    public Collection<AccountDTO> getAllAccountDTOs() {
        return accountManager.getAllAccounts()
                .stream()
                .map(AccountConverter::accountToDTO)
                .collect(Collectors.toList());
    }
}

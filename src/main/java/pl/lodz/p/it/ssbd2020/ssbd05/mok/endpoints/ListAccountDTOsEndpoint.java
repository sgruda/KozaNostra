package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.converters.AccountConverter;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

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
    private AccountFacade accountFacade;

    public Collection<AccountDTO> getAllAccountDTOs() {
        return accountFacade.getAllAccounts()
                .stream()
                .map(AccountConverter::accountToDTO)
                .collect(Collectors.toList());
    }
}

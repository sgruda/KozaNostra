package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.converters.AccountConverter;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateless
public class AccountDTODetailsEndpoint implements Serializable {

    @Inject
    AccountManager accountManager;

    public AccountDTO getAccountDTO(Long id) {
        return AccountConverter.accountToDTO(accountManager.findById(id));
    }
}

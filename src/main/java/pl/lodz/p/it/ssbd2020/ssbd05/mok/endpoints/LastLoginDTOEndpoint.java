package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.converters.AccountConverter;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateless
public class LastLoginDTOEndpoint implements Serializable {
    @Inject
    private AccountManager accountManager;

    //TODO: nie korzystać z AccountFacade
    @Inject
    private AccountFacade accountFacade;

    public AccountDTO findByLogin(String username) {
        return AccountConverter.accountToDTO(accountManager.findByLogin(username));
    }
    public void edit(AccountDTO accountDTO) {
        accountManager.edit(AccountConverter.DTOtoAccount(accountDTO, this.accountFacade));
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.converters.AccountConverter;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateless
public class ConfirmAccountDTOEndpoint implements Serializable {

    @Inject
    private AccountFacade accountFacade;

    public AccountDTO getAccountByLogin(String login) {
        return AccountConverter.accountToDTO(accountFacade.findByLogin(login));
    }

    public void confirmAccount(AccountDTO accountDTO) {
        accountFacade.edit(AccountConverter.DTOtoAccount(accountDTO));
    }
}

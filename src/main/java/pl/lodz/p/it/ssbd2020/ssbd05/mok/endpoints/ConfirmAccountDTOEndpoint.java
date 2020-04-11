package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.converters.AccountConverter;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateful
public class ConfirmAccountDTOEndpoint implements Serializable {

    @Inject
    private AccountFacade accountFacade;

    public Account getAccountByLogin(String login) {
        //return AccountConverter.accountToDTO(accountFacade.findByLogin(login));
        return accountFacade.findByLogin(login);
    }

    public void confirmAccount(Account accountDTO) {
        //accountFacade.edit(AccountConverter.DTOtoAccount(accountDTO));
        accountFacade.edit(accountDTO);
//        Account account = accountFacade.findByLogin("testowanie");
//        account.setConfirmed(true);
//        accountFacade.edit(account);
    }
}

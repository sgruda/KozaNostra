package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ListAccountsEndpointLocal {
    Collection<AccountDTO> getAllAccounts();

    Collection<AccountDTO> filterAccounts(String accountFilter);
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ListAccountsEndpointLocal {
    Collection<AccountDTO> getAllAccounts() throws AppBaseException;

    Collection<AccountDTO> filterAccounts(String accountFilter) throws AppBaseException;
}

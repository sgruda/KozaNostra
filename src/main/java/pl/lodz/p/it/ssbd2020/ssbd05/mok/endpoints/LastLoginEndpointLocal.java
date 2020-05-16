package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

@Local
public interface LastLoginEndpointLocal {
    String getFailedAttemptNumberFromProperties() throws AppBaseException;

    AccountDTO findByLogin(String username);

    void edit(AccountDTO accountDTO) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ChangeAccessLevelEndpointLocal {
    AccountDTO findByLogin(String username) throws AppBaseException;
    void changeAccessLevel(AccountDTO accountDTO) throws AppBaseException;
}

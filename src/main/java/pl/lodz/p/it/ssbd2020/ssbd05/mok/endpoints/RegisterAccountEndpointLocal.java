package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface RegisterAccountEndpointLocal {
    void addNewAccount (AccountDTO accountDTO) throws AppBaseException;

    Collection<AccessLevel> generateAccessLevels() throws AppBaseException;

    Account getAccount();

    Collection<AccessLevel> getAccessLevels();

    void setAccount(pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account account);

    void setAccessLevels(Collection<AccessLevel> accessLevels);
}

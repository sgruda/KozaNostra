package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public interface EditAccountEndpointLocal {
    AccountDTO findByLogin(String username);
    void changePassword(String newPassword, AccountDTO accountDTO) throws AppBaseException;
    void edit(AccountDTO accountDTO) throws AppBaseException;
    void blockAccount(AccountDTO accountDTO) throws AppBaseException;
    void unlockAccount(AccountDTO accountDTO) throws AppBaseException;
}

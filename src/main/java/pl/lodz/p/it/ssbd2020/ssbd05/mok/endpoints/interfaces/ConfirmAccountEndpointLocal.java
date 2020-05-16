package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public interface ConfirmAccountEndpointLocal {
    AccountDTO getAccountByToken(String token) throws AppBaseException;
    void confirmAccount() throws AppBaseException;
}

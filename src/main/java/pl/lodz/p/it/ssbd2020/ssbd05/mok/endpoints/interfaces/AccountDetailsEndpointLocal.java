package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;

import javax.ejb.Local;

@Local
public interface AccountDetailsEndpointLocal {

    AccountDTO getAccount(String login);
    AccountDTO getOwnAccount();
}
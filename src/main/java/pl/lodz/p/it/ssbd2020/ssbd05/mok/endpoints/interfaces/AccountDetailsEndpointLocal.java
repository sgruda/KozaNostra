package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego AccountDetailsEndpoint pośredniczącego
 * przy wyświetlaniu szczegółów własnego konta oraz konta użytkownika wybranego przez administratora
 */
@Local
public interface AccountDetailsEndpointLocal {

    /**
     * Metoda odpowiedzialna za wyszukiwanie konta użytkownika
     * wybranego przez administratora o podanej nazwie uzytkownika
     *
     * @param login nazwa użytkownika
     * @return obiekt konta użytkownika
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    AccountDTO getAccount(String login) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za wyszukiwanie konta użytkownika obecnie zalogowanego
     *
     * @return obiekt konta użytkownika obecnie zalogowanego
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    AccountDTO getOwnAccount() throws AppBaseException;
}

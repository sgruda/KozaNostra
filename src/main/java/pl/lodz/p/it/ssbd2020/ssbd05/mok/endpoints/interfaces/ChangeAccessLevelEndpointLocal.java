package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
/**
 * Interfejs dla punktu dostępowego ChangeAccessLevelEndpoint pośredniczącemu
 * w zmianie (dodaniu albo odjęciu) poziomów dostępów udostępnionych użytkownikowi.
 *
 */
@Local
public interface ChangeAccessLevelEndpointLocal {
    /**
     * Metoda odpowiedzialna za wyszukanie konta po loginie
     *
     * @param username nazwa użytkownika
     * @return obiekt typu AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    AccountDTO findByLogin(String username) throws AppBaseException;
    /**
     * Metoda odpowiedzialna za zmianę poziomów dostępu danego konta
     *
     * @param accountDTO Obiekt klasy AccountDTO ze zmienionymi, przypisanymi poziomami dostępu.
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void changeAccessLevel(AccountDTO accountDTO) throws AppBaseException;
}

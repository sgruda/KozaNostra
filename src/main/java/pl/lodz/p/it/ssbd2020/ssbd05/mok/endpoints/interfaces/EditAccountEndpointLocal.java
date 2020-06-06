package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego EditAccountEndpoint pośredniczącemu
 * przy edycji kont
 *
 */
@Local
public interface EditAccountEndpointLocal {
    /**
     * Metoda odpowiedzialna za wyszukanie konta po loginie
     *
     * @param username nazwa użytkownika
     * @return obiekt typu AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    AccountDTO findByLogin(String username) throws AppBaseException;
    /**
     * Metoda odpowiedzialna za zmianę hasła własnego konta
     *
     * @param accountDTO obiekt typu AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void changePassword(AccountDTO accountDTO) throws AppBaseException;
    /**
     * Metoda odpowiedzialna za zmianę hasła cudzego konta
     *
     * @param accountDTO obiekt typu AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void changeOtherAccountPassword(AccountDTO accountDTO) throws AppBaseException;
    /**
     * Metoda odpowiedzialna za edycję konta
     *
     * @param accountDTO obiekt typu AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void editAccount(AccountDTO accountDTO) throws AppBaseException;
    /**
     * Metoda odpowiedzialna za blokowanie konta
     *
     * @param accountDTO obiekt typu AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void blockAccount(AccountDTO accountDTO) throws AppBaseException;
    /**
     * Metoda odpowiedzialna za odblokowanie konta
     *
     * @param accountDTO obiekt typu AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void unlockAccount(AccountDTO accountDTO) throws AppBaseException;
}

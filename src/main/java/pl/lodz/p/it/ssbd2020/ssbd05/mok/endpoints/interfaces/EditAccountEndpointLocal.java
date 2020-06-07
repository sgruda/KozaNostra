package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego EditAccountEndpoint wykorzystywanego do edycji kont użytkowników
 */
@Local
public interface EditAccountEndpointLocal {

    /**
     * Metoda pobierająca konto do jego późniejszej edycji
     *
     * @param username Nazwa użytkownika
     * @return Obiekt typu AccountDTO
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    AccountDTO findByLogin(String username) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za zmianę własnego hasła
     *
     * @param accountDTO Obiekt typu AccountDTO
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    void changePassword(AccountDTO accountDTO) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za zmianę hasła innego użytkownika
     *
     * @param accountDTO Obiekt typu AccountDTO
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    void changeOtherAccountPassword(AccountDTO accountDTO) throws AppBaseException;
    void editAccount(AccountDTO accountDTO) throws AppBaseException;
    void blockAccount(AccountDTO accountDTO) throws AppBaseException;
    void unlockAccount(AccountDTO accountDTO) throws AppBaseException;
}

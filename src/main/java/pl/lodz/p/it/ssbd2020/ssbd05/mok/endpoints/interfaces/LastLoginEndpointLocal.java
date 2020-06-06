package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego LastLoginEndpoint pośredniczącemu w operacji uwierzytelniania
 */
@Local
public interface LastLoginEndpointLocal {
    /**
     * Metoda pobierająca liczbę nieudanych prób uwierzytelnienia
     *
     * @return Liczba nieudanych prób uwierzytelnienia
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    String getFailedAttemptNumberFromProperties() throws AppBaseException;

    /**
     * Metoda pobierająca obiekt DTO użytkownika na podstawie podanej nazwy użytkownika.
     *
     * @param username nazwa użytkownika dokonującego próby uwierzytelenienia
     * @return obiekt DTO użytkownika
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    AccountDTO getAccountByLogin(String username) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za edycję danych konta przy próbie uwierzytelnienia
     *
     * @param accountDTO obiekt DTO konta dokonującego próby uwierzytelnienia
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void edit(AccountDTO accountDTO) throws AppBaseException;
}

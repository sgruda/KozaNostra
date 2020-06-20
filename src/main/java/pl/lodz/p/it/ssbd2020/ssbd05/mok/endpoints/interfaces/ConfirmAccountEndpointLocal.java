package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego ConfirmAccountEndpoint pośredniczącego w potwierdzaniu konta użytkownika.
 */
@Local
public interface ConfirmAccountEndpointLocal {

    /**
     * Metoda odpowiedzialna za wyszukanie konta po jednorazowym kodzie weryfikującym
     *
     * @param token nazwa użytkownika
     * @return obiekt typu AccountDTO
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    AccountDTO getAccountByToken(String token) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za potwierdzenie konta
     *
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    void confirmAccount() throws AppBaseException;
}

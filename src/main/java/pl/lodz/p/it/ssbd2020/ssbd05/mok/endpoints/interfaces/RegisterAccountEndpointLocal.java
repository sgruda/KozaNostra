package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego RegisterAccountEndpoint pośredniczącego
 * w rejestracji konta użytkownika.
 */
@Local
public interface RegisterAccountEndpointLocal {
    /**
     * Metoda dodająca nowe konto.
     *
     * @param accountDTO Obiekt typu AccountDTO, reprezentujący nowostworzone konto.
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void addNewAccount (AccountDTO accountDTO) throws AppBaseException;
}

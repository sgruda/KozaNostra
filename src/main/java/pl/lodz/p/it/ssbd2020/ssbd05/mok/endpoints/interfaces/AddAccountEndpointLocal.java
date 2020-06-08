package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
/**
 * Interfejs dla punktu dostępowego AddAccountEndpoint pośredniczącemu
 * w tworzeniu przez administratora konta użytkownika.
 */
@Local
public interface AddAccountEndpointLocal {
    /**
     * Metoda odpowiedzialna za tworzenie konta
     *
     * @param accountDTO Obiekt klasy AccountDTO, reprezentujący nowostworzone konto.
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void addAccount(AccountDTO accountDTO) throws AppBaseException;
}

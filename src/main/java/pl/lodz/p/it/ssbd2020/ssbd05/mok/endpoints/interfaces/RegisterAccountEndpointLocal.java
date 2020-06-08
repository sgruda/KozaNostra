package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.Collection;
/**
 * Interfejs dla punktu dostępowego RegisterAccountEndpoint pośredniczącemu
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

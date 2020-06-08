package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.Collection;

/**
 * Interfejs dla punktu dostępowego ListAccountsEndpoint pośredniczącemu
 * przy wyświetlaniu wszystkich kont w systemie oraz filtrowaniu ich
 *
 */
@Local
public interface ListAccountsEndpointLocal {
    /**
     * Metoda odpowiedzialna za pobranie wszystkich kont systemu
     *
     * @return wszystkie konta
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    Collection<AccountDTO> getAllAccounts() throws AppBaseException;

    /**
     * Metoda odpowiedzialna za filtrowanie listy kont zgodnie z filtrem
     *
     * @param accountFilter filtr
     * @return kolekcja kont
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    Collection<AccountDTO> filterAccounts(String accountFilter) throws AppBaseException;
}

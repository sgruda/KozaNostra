package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego ListReservationEndpoint pośredniczącemu
 * przy wyświetlaniu listy rezerwacji wszystkich użytkowników
 */
@Local
public interface ListReservationEndpointLocal {
    /**
     * Metoda odpowiedzialna za pobranie listy wszystkich rezerwacji.
     *
     * @return lista wszystkich rezerwacji w postacji obiektów typu ReservationDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ReservationDTO> getAllReservations() throws AppBaseException;

    /**
     * Metoda odpowiedzalna za pobranie przefiltrowanej listy rezerwacji.
     *
     * @param filter filtr, rezerwacje filtrowane są po loginie, imieniu, nazwisku oraz numerze rezerwacji. Wielkość liter nie ma znaczenia.
     * @return przefiltrowana lista rezerwacji w postaci obiektów typu ReservationDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ReservationDTO> filterReservations(String filter) throws AppBaseException;
}

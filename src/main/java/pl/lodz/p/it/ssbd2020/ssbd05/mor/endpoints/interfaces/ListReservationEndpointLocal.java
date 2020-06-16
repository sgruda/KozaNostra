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
     * Filter reservations list.
     *
     * @param filter the filter
     * @return the list
     * @throws AppBaseException the app base exception
     */
    List<ReservationDTO> filterReservations(String filter) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego ListUserReservationsEndpoint pośredniczącemu
 * przy wyświetlaniu listy rezerwacji użytkownika
 */
@Local
public interface ListUserReservationsEndpointLocal {
    /**
     * Pobierz wszystkie rezerwacje użytkownika o podanej nazwie użytkownika
     *
     * @param login nazwa użytkownika
     * @return lista wszystkich rezerwacji użytkownika
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ReservationDTO> getAllUsersReservations(String login) throws AppBaseException;

    /**
     * Gets user reviewable reservations.
     *
     * @param login the login
     * @return the user reviewable reservations
     * @throws AppBaseException the app base exception
     */
    List<ReservationDTO> getUserReviewableReservations(String login) throws AppBaseException;


}

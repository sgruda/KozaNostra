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
     * Metoda zwracająca listę rezerwacji w postaci obiektów ReservationDTO, dla których możliwe jest dodanie opinii przez konto z podaną nazwą użytkownika.
     *
     * @param login nazwa użykownika
     * @return lista rezerwacji dla których możliwe jest wystawienie opinii
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ReservationDTO> getUserReviewableReservations(String login) throws AppBaseException;


}

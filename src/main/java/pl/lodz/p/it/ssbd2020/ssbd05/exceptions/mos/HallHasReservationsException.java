package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie usunięcia sali, która ma przypisane rezerwacje.
 */
public class HallHasReservationsException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String KEY_HALL_PROBLEM = "page.hall.details.reservation.exists";

    /**
     * Konstruktor bezparametrowy.
     */
    public HallHasReservationsException() {
        super(KEY_HALL_PROBLEM);
    }

}

package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie edycji lub usunięcia aktywnej sali.
 */
public class HallActiveException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String KEY_HALL_ACTIVE_PROBLEM = "error.hall.active";

    /**
     * Konstruktor bezparametrowy.
     */
    public HallActiveException() {
        super(KEY_HALL_ACTIVE_PROBLEM);
    }
}

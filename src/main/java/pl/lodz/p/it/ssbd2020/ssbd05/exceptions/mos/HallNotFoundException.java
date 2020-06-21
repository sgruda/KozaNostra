package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany, gdy dana sala nie zostanie odnaleziona.
 */
public class HallNotFoundException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String HALL_NOT_FOUND = "error.hall.not.found";

    /**
     * Konstruktor bezparametrowy.
     */
    public HallNotFoundException() {
        super(HALL_NOT_FOUND);
    }

}

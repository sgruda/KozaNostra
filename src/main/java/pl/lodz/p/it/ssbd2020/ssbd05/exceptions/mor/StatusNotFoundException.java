package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany, gdy dany status nie zostanie odnaleziony.
 */
public class StatusNotFoundException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String STATUS_NOT_FOUND = "error.status.not.found";

    /**
     * Konstruktor bezparametrowy.
     */
    public StatusNotFoundException() {
        super(STATUS_NOT_FOUND);
    }

}
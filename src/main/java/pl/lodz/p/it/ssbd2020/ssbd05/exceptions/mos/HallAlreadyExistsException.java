package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie dodania sali o nazwie, która już istnieje w systemie.
 */
public class HallAlreadyExistsException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String KEY_HALL_PROBLEM = "error.hall.exists";

    /**
     * Konstruktor z przyczyną.
     *
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public HallAlreadyExistsException(Throwable cause) {
        super(KEY_HALL_PROBLEM, cause);
    }

}

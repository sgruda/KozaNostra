package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie zapisu do obiektu encji, który narusza ograniczenia bazodanowe.
 */
public class DatabaseQueryException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_DATABASE_QUERY_PROBLEM = "error.database.query.problem";

    /**
     * Konstruktor z przyczyną.
     *
     * @param ex   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public DatabaseQueryException(Throwable ex) {
        super(KEY_DATABASE_QUERY_PROBLEM, ex);
    }

}

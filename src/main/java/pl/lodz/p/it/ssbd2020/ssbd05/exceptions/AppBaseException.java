package pl.lodz.p.it.ssbd2020.ssbd05.exceptions;

import javax.ejb.ApplicationException;

/**
 * Bazowa klasa wyjątku aplikacyjnego odwołująca transakcję.
 */
@ApplicationException(rollback = true)
public class AppBaseException extends Exception {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_DEFAULT = "error.default";

    /**
     * Konstruktor bezparametrowy.
     */
    public AppBaseException() {
        super(KEY_DEFAULT);
    }

    /**
     * Konstruktor z wiadomością.
     *
     * @param message Treść wiadomości.
     */
    public AppBaseException(String message) {
        super(message);
    }

    /**
     * Konstruktor z wiadomością i przyczyną.
     *
     * @param message Wiadomość.
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

}

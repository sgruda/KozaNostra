package pl.lodz.p.it.ssbd2020.ssbd05.exceptions;

/**
 * Wyjątek aplikacyjny rzucany przy braku poprawności ograniczeń Bean Validation.
 */
public class ValidationException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String VALIDATION_ERROR = "error.validation";

    /**
     * Konstruktor bezparametrowy.
     */
    public ValidationException() {
        super(VALIDATION_ERROR);
    }

    /**
     * Konstruktor z wiadomością.
     *
     * @param message Treść wiadomości.
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Konstruktor z przyczyną.
     *
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public ValidationException(Throwable cause) {
        super(VALIDATION_ERROR, cause);
    }
}

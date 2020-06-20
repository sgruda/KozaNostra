package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany, gdy dana recenzja nie zostanie odnaleziona.
 */
public class ReviewNotFoundException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String REVIEW_NOT_FOUND = "error.review.not.found";

    /**
     * Konstruktor bezparametrowy.
     */
    public ReviewNotFoundException() {
        super(REVIEW_NOT_FOUND);
    }

    /**
     * Konstruktor z przyczyną.
     *
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public ReviewNotFoundException(Throwable cause) {
        super(REVIEW_NOT_FOUND, cause);
    }
}
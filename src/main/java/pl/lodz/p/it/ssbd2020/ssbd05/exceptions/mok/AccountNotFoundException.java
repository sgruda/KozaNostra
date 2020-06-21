package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany, gdy dane konto nie zostanie odnalezione.
 */
public class AccountNotFoundException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_ACCOUNT_NOT_FOUND = "error.account.not.found";

    /**
     * Konstruktor z wiadomością.
     *
     * @param message Treść wiadomości.
     */
    public AccountNotFoundException(String message) {
        super(message);
    }

    /**
     * Konstruktor z przyczyną.
     *
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public AccountNotFoundException(Throwable cause) {
        super(KEY_ACCOUNT_NOT_FOUND, cause);
    }
}

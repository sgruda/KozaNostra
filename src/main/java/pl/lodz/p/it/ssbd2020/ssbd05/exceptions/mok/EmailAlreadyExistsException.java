package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie dodania konta o adresie email, który wcześniej został zarejestrowany w systemie.
 */
public class EmailAlreadyExistsException  extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_ACCOUNT_EMAIL_PROBLEM = "error.account.email.exists";

    /**
     * Konstruktor z przyczyną.
     *
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public EmailAlreadyExistsException(Throwable cause) {
        super(KEY_ACCOUNT_EMAIL_PROBLEM, cause);
    }
}

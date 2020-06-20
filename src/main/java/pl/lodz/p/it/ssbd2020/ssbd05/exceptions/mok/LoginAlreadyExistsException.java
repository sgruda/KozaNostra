package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie dodania konta o loginie, który wcześniej został zarejestrowany w systemie.
 */
public class LoginAlreadyExistsException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_ACCOUNT_LOGIN_PROBLEM = "error.account.login.exists";

    /**
     * Konstruktor z przyczyną.
     *
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public LoginAlreadyExistsException(Throwable cause) {
        super(KEY_ACCOUNT_LOGIN_PROBLEM, cause);
    }

}

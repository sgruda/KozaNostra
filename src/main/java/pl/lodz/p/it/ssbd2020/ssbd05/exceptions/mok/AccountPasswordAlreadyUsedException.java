package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany podczas próby zmiany hasła na takie, które było w przeszłości użyte przez użytkownika.
 */
public class AccountPasswordAlreadyUsedException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_ACCOUNT_PASSWORD_PROBLEM = "error.account.password.already.used";

    /**
     * Konstruktor bezparametrowy.
     */
    public AccountPasswordAlreadyUsedException() {
        super(KEY_ACCOUNT_PASSWORD_PROBLEM);
    }

}

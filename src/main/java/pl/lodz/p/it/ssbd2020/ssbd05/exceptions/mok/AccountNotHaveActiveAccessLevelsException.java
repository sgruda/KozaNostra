package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie zapisu konta, do którego nie przypisano żadnego poziomu dostępu.
 */
public class AccountNotHaveActiveAccessLevelsException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_ACCOUNT_NOT_HAVE_ACTIVE_ACCESS_LEVELS = "error.account.not.have.active.access.levels";

    /**
     * Konstruktor bezparametrowy.
     */
    public AccountNotHaveActiveAccessLevelsException() {
        super(KEY_ACCOUNT_NOT_HAVE_ACTIVE_ACCESS_LEVELS);
    }

}
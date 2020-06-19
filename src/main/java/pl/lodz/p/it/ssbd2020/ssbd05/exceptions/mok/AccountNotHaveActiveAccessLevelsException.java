package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountNotHaveActiveAccessLevelsException extends AppBaseException {
    static final public String KEY_ACCOUNT_NOT_HAVE_ACTIVE_ACCESS_LEVELS = "error.account.not.have.active.access.levels";


    public AccountNotHaveActiveAccessLevelsException() {
        super(KEY_ACCOUNT_NOT_HAVE_ACTIVE_ACCESS_LEVELS);
    }

    public AccountNotHaveActiveAccessLevelsException(String message) {
        super(KEY_ACCOUNT_NOT_HAVE_ACTIVE_ACCESS_LEVELS);
    }

    public AccountNotHaveActiveAccessLevelsException(Throwable cause) {
        super(KEY_ACCOUNT_NOT_HAVE_ACTIVE_ACCESS_LEVELS, cause);
    }
}
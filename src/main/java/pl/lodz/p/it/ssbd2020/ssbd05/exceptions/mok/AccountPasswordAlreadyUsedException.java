package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountPasswordAlreadyUsedException extends AppBaseException {
    static final public String KEY_ACCOUNT_PASSWORD_PROBLEM = "error.account.password.already.used";


    public AccountPasswordAlreadyUsedException() {
        super(KEY_ACCOUNT_PASSWORD_PROBLEM);
    }

    public AccountPasswordAlreadyUsedException(String message) {
        super(message);
    }

    public AccountPasswordAlreadyUsedException(Throwable cause) {
        super(KEY_ACCOUNT_PASSWORD_PROBLEM, cause);
    }
}

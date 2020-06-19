package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountNotFoundException extends AppBaseException {
    static final public String KEY_ACCOUNT_NOT_FOUND = "error.account.not.found";


    public AccountNotFoundException() {
        super(KEY_ACCOUNT_NOT_FOUND);
    }

    public AccountNotFoundException(String message) {
        super(KEY_ACCOUNT_NOT_FOUND);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}

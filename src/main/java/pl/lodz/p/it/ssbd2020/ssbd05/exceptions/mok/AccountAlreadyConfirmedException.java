package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountAlreadyConfirmedException extends AppBaseException {
    static final public String KEY_CONFIRMED = "error.account.confirmed";


    public AccountAlreadyConfirmedException() {super(KEY_CONFIRMED);}

    public AccountAlreadyConfirmedException(String message) {
        super(KEY_CONFIRMED);
    }

    public AccountAlreadyConfirmedException(String message, Throwable cause) {
        super(KEY_CONFIRMED, cause);
    }
}
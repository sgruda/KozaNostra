package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountAlreadyConfirmedException extends AppBaseException {
    static final public String KEY_CONFIRMED = "error.account.confirmed";

    public AccountAlreadyConfirmedException(String message) {
        super(message);
    }
}
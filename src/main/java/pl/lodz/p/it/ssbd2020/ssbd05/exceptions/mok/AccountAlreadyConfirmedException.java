package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountAlreadyConfirmedException extends AppBaseException {
    public AccountAlreadyConfirmedException() {super();}

    public AccountAlreadyConfirmedException(String message) {
        super(message);
    }

    public AccountAlreadyConfirmedException(String message, Throwable cause) {
        super(message, cause);
    }
}
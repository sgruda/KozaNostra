package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountAlreadyConfirmedException extends AppBaseException {
    public AccountAlreadyConfirmedException(String message) {
        super(message);
    }
}
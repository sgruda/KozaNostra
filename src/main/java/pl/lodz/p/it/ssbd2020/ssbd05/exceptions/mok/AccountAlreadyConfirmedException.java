package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie potwierdzenia już potwierdzonego konta użytkownika.
 */
public class AccountAlreadyConfirmedException extends AppBaseException {

    /**
     * Konstruktor z wiadomością.
     *
     * @param message Treść wiadomości.
     */
    public AccountAlreadyConfirmedException(String message) {
        super(message);
    }
}
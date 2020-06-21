package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany, gdy dana usługa dodatkowa nie zostanie odnaleziona.
 */
public class ExtraServiceNotFoundException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String SERVICE_NOT_FOUND = "error.extraservice.not.found";

    /**
     * Konstruktor bezparametrowy.
     */
    public ExtraServiceNotFoundException() {
        super(SERVICE_NOT_FOUND);
    }

    /**
     * Konstruktor z wiadomością.
     *
     * @param message Treść wiadomości.
     */
    public ExtraServiceNotFoundException(String message) {
        super(message);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie dodania usługi dodatkowej o nazwie, która już istnieje w systemie.
 */
public class ExtraServiceAlreadyExistsException extends AppBaseException {

    /**
     * Konstruktor z wiadomością.
     *
     * @param message Treść wiadomości.
     */
    public ExtraServiceAlreadyExistsException(String message) {
        super(message);
    }
}

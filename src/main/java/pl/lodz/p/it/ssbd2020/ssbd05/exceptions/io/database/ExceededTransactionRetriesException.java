package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany po przekroczeniu maksymalnej dozwolonej liczby ponowień transakcji.
 */
public class ExceededTransactionRetriesException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_TRANSACTION_RETIRES_EXCEEDED= "error.transaction.number.exceeded";

    /**
     * Konstruktor bezparametrowy.
     */
    public ExceededTransactionRetriesException() {
        super(KEY_TRANSACTION_RETIRES_EXCEEDED);
    }

    /**
     * Konstruktor z wiadomością.
     *
     * @param msg Treść wiadomości.
     */
    public ExceededTransactionRetriesException(String msg) {
        super(msg);
    }

    /**
     * Konstruktor z przyczyną.
     *
     * @param ex   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public ExceededTransactionRetriesException(Throwable ex) {
        super(KEY_TRANSACTION_RETIRES_EXCEEDED, ex);
    }
}

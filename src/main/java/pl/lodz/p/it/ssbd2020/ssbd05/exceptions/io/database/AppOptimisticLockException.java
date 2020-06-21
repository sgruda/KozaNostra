package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie zapisu obiektu encji o niezgodnym numerze wersji w bazie danych.
 */
public class AppOptimisticLockException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_DATABASE_OPTIMISTIC_LOCK = "error.database.optimistic.lock";

    /**
     * Konstruktor bezparametrowy.
     */
    public AppOptimisticLockException() {
        super(KEY_DATABASE_OPTIMISTIC_LOCK);
    }

    /**
     * Konstruktor z wiadomością.
     *
     * @param msg Treść wiadomości.
     */
    public AppOptimisticLockException(String msg) {
        super(msg);
    }

    /**
     * Konstruktor z przyczyną.
     *
     * @param ex   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public AppOptimisticLockException(Throwable ex) {
        super(KEY_DATABASE_OPTIMISTIC_LOCK, ex);
    }
}
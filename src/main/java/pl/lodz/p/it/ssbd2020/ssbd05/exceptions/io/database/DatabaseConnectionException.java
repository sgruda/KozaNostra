package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy wystąpieniu problemu z połączeniem z bazą danych.
 */
public class DatabaseConnectionException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_DATABASE_CONNECTION_PROBLEM = "error.database.connection.problem";

    /**
     * Konstruktor bezparametrowy.
     */
    public DatabaseConnectionException() {
        super(KEY_DATABASE_CONNECTION_PROBLEM);
    }

    /**
     * Konstruktor z wiadomością.
     *
     * @param msg Treść wiadomości.
     */
    public DatabaseConnectionException(String msg){
        super(msg);
    }

    /**
     * Konstruktor z przyczyną.
     *
     * @param ex   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public DatabaseConnectionException(Throwable ex){
        super(KEY_DATABASE_CONNECTION_PROBLEM, ex);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.exceptions;

import org.eclipse.persistence.exceptions.DatabaseException;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AppBaseException extends Exception {

    static final public String KEY_DATABASE_CONNECTION_PROBLEM = "error.database.connection.problem";
    static final public String KEY_DATABASE_QUERY_PROBLEM = "error.database.query.problem";

    public AppBaseException() {
        super();
    }

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AppBaseException DatabaseConnectionException(Throwable ex) {
        return new AppBaseException(KEY_DATABASE_CONNECTION_PROBLEM,ex);
    }

    public static AppBaseException DatabaseQueryException(Throwable ex) {
        return new AppBaseException(KEY_DATABASE_QUERY_PROBLEM,ex);
    }
}

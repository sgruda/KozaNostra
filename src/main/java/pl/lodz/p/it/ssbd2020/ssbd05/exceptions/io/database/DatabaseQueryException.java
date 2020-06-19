package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class DatabaseQueryException extends AppBaseException {
    static final public String KEY_DATABASE_QUERY_PROBLEM = "error.database.query.problem";

    public DatabaseQueryException(Throwable ex) {
        super(KEY_DATABASE_QUERY_PROBLEM, ex);
    }

}

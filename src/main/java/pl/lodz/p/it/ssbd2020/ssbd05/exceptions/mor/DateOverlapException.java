package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class DateOverlapException extends AppBaseException {
    static final public String DATES_OVERLAP= "error.createreservation.dates.overlap";

    public DateOverlapException() {
        super(DATES_OVERLAP);
    }

    public DateOverlapException(String message, Throwable cause) {
        super(DATES_OVERLAP, cause);
    }
}

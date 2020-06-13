package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class DatesOverlapException extends AppBaseException {
    static final public String KEY_DATES_OVERLAP= "error.createreservation.dates.overlap";


    public DatesOverlapException() {
        super();
    }

    public DatesOverlapException(String message) {
        super(message);
    }

    public DatesOverlapException(Throwable cause) {
        super(KEY_DATES_OVERLAP, cause);
    }
}

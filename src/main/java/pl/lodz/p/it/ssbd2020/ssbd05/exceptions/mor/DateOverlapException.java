package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie zarezerwowania sali w okienku czasowym,
 * które koliduje z inną rezerwacją na tej sali.
 */
public class DateOverlapException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String DATES_OVERLAP= "error.createreservation.dates.overlap";

    /**
     * Konstruktor bezparametrowy.
     */
    public DateOverlapException() {
        super(DATES_OVERLAP);
    }
}

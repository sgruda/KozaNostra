package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class HallNotFoundException extends AppBaseException {

    public static final String HALL_NOT_FOUND = "error.hall.not.found";

    public HallNotFoundException() {
        super(HALL_NOT_FOUND);
    }

}

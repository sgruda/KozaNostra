package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AddressNotFoundException extends AppBaseException {

    public static final String KEY_ADDRESS_NOT_FOUND = "error.address.not.found";

    public AddressNotFoundException() {
        super(KEY_ADDRESS_NOT_FOUND);
    }

    public AddressNotFoundException(Throwable cause) {
        super(KEY_ADDRESS_NOT_FOUND, cause);
    }
}

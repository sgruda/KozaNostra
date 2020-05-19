package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AddressAlreadyExistsException extends AppBaseException {

    public static final String KEY_ADDRESS_PROBLEM = "error.address.exists";

    public AddressAlreadyExistsException() {
        super();
    }

    public AddressAlreadyExistsException(String message) {
        super(message);
    }

    public AddressAlreadyExistsException(String message, Throwable cause) {
        super(KEY_ADDRESS_PROBLEM, cause);
    }
}

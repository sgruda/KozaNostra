package pl.lodz.p.it.ssbd2020.ssbd05.exceptions;

public class ValidationException extends AppBaseException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.exceptions;

public class ValidationException extends AppBaseException {
    static final public String VALIDATION_ERROR = "error.validation";

    public ValidationException() {
        super(VALIDATION_ERROR);
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}

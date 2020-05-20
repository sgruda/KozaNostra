package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ReviewAlreadyExistsException extends AppBaseException {
    static final public String KEY_REVIEW_EXISTS= "error.review.exists";


    public ReviewAlreadyExistsException() {
        super();
    }

    public ReviewAlreadyExistsException(String message) {
        super(message);
    }

    public ReviewAlreadyExistsException(Throwable cause) {
        super(KEY_REVIEW_EXISTS, cause);
    }
}

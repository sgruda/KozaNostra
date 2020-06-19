package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ReviewNotFoundException extends AppBaseException {
    public static final String REVIEW_NOT_FOUND = "error.review.not.found";

    public ReviewNotFoundException() {
        super(REVIEW_NOT_FOUND);
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(Throwable cause) {
        super(cause);
    }
}
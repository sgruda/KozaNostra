package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ReviewFacade;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ReviewManager extends AbstractManager implements SessionSynchronization  {
    @Inject
    private ReviewFacade reviewFacade;

    @RolesAllowed("getReviewByReviewNumber")
    public Review getReviewByReviewNumber(String reviewNumber) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("removeReview")
    public void removeReview(Review review) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("addReview")
    public void addReview(Review review) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("addReview")
    public void editReview(Review review) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @PermitAll
    public List<Review> getAllReviews() throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

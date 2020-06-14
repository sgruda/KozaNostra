package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import lombok.extern.java.Log;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ClientFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ReservationFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ReviewFacade;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.PersistenceException;
import java.util.List;

@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ReviewManager extends AbstractManager implements SessionSynchronization  {
    @Inject
    private ReviewFacade reviewFacade;

    @Inject
    ClientFacade clientFacade;

    @Inject
    ReservationFacade reservationFacade;

    @RolesAllowed("getReviewByReviewNumber")
    public Review getReviewByReviewNumber(String reviewNumber) throws AppBaseException {
        return reviewFacade.findByNumber(reviewNumber).get();
    }

    @RolesAllowed("removeReview")
    public void removeReview(Review review) throws AppBaseException {
        reviewFacade.remove(review);
    }

    @RolesAllowed("addReview")
    public void addReview(Review review, String clientLogin, String reservationNumber) throws AppBaseException {
        try{
            review.setClient(clientFacade.findByLogin(clientLogin));
            review.setReservation(reservationFacade.findByNumber(reservationNumber).get());
            reviewFacade.create(review);
        }catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @RolesAllowed("editReview")
    public void editReview(Review review) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @PermitAll
    public List<Review> getAllReviews() throws AppBaseException {
        return reviewFacade.findAll();
    }
}

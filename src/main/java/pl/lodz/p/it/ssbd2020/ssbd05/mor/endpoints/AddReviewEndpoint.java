package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReviewMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.AddReviewEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReviewManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Punkt dostępowy implementujący interfejs AddReviewEndpointLocal, który pośredniczy
 * przy dodawaniu nowej opinii.
 */
@Stateful
@Log
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class AddReviewEndpoint implements AddReviewEndpointLocal, Serializable {

    @Inject
    private ReviewManager reviewManager;
    private Review review;



    @Override
    @RolesAllowed("addReview")
    public void addReview(ReviewDTO reviewDTO) throws AppBaseException {
        review = ReviewMapper.INSTANCE.createNewReview(reviewDTO);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                String clientLogin = reviewDTO.getClientLogin();
                String reservationNumber = reviewDTO.getReservationNumber();
                review.setDate(LocalDateTime.now());
                reviewManager.addReview(review, clientLogin, reservationNumber);
                rollback = reviewManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + reviewManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}
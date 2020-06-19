package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReviewMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReviewEndpointLocal;
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
/**
 * Punkt dostępowy implementujący interfejs EditReviewEndpointLocal, który pośredniczy
 * przy edycji opinii
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class EditReviewEndpoint implements EditReviewEndpointLocal, Serializable {
    @Inject
    private ReviewManager reviewManager;
    private Review review;


    @Override
    @RolesAllowed("getReviewByReviewNumber")
    public ReviewDTO getReviewByReviewNumber(String reviewNumber) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                review = reviewManager.getReviewByReviewNumber(reviewNumber);
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
        return ReviewMapper.INSTANCE.toReviewDTO(review);
    }

    @Override
    @RolesAllowed("editReview")
    public void editReview(ReviewDTO reviewDTO) throws AppBaseException{
        ReviewMapper.INSTANCE.updateAndCheckReviewFromDTO(reviewDTO, review);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reviewManager.editReview(review);
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
    @Override
    @RolesAllowed("removeReview")
    public void removeReview(ReviewDTO reviewDTO) throws AppBaseException {
        ReviewMapper.INSTANCE.updateAndCheckReviewFromDTO(reviewDTO, review);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reviewManager.removeReview(review);
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

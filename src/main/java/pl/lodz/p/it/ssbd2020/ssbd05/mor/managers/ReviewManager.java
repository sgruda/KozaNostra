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
/**
 * Klasa odpowiedzialna za operacje na obiektach encyjnych typu Review
 */
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ReviewManager extends AbstractManager implements SessionSynchronization  {
    @Inject
    private ReviewFacade reviewFacade;

    @Inject
    private ClientFacade clientFacade;

    @Inject
    private ReservationFacade reservationFacade;

    /**
     * Metoda odpowiedzialna za pobieranie opinii na podstawie jej numeru.
     *
     * @param reviewNumber Numer opinii
     * @return Obiekt typu Review reprezentujący opinię
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getReviewByReviewNumber")
    public Review getReviewByReviewNumber(String reviewNumber) throws AppBaseException {
        return reviewFacade.findByNumber(reviewNumber).get();
    }
    /**
     * Metoda odpowiedzialna za usunięcie opinii.
     *
     * @param review Obiekt typu Review
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("removeReview")
    public void removeReview(Review review) throws AppBaseException {
        reviewFacade.remove(review);
    }

    /**
     * Metoda odpowiedzialna za dodanie opinii
     *
     * @param review            opinia
     * @param clientLogin       nazwa użytkownika
     * @param reservationNumber numer rezerwacji
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
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
    /**
     * Metoda odpowiedzialna za edycję opinii.
     *
     * @param review Obiekt typu Review
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("editReview")
    public void editReview(Review review) throws AppBaseException {
        reviewFacade.edit(review);
    }
    /**
     * Metoda odpowiedzialna za pobranie listy wszystkich opinii
     *
     * @return lista obiektów typu Review
     * @throws AppBaseException podstawowy wyjatek aplikacyjny
     */
    @PermitAll
    public List<Review> getAllReviews() throws AppBaseException {
        return reviewFacade.findAll();
    }
}

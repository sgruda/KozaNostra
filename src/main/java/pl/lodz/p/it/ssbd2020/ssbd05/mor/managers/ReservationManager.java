package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReservationNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Reservation manager.
 */
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ReservationManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ReservationFacade reservationFacade;

    @Inject
    private EventTypesFacade eventTypesFacade;

    @Inject
    private StatusFacade statusFacade;

    @Inject
    private HallFacade hallFacade;

    @Inject
    private AccountFacade accountFacade;
    @Inject
    private ClientFacade clientFacade;

    /**
     * Pobieranie listy wszystkich rezerwacji
     *
     * @return List<Reservation>
     * @throws AppBaseException podstawowy wyjatek aplikacyjny
     */
    @RolesAllowed("getAllReservations")
    public List<Reservation> getAllReservations() throws AppBaseException {
        return reservationFacade.findAll();
    }

    /**
     * Gets all event types.
     *
     * @return the all event types
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getAllEventTypes")
    public List<EventType> getAllEventTypes() throws AppBaseException {
        return eventTypesFacade.findAll();
    }

    @RolesAllowed("getHallByName")
    public Hall getHallByName(String name) throws AppBaseException {
        if (hallFacade.findByName(name).isPresent()) {
            return hallFacade.findByName(name).get();
        } else {
            throw new HallNotFoundException();
        }
    }


    /**
     * Tworzenie nowej rezerwacji
     *
     * @param reservation Rezerwacja
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("createReservation")
    public void createReservation(Reservation reservation) throws AppBaseException{
        reservationFacade.create(reservation);
    }

    @RolesAllowed("getAllUsersReservations")
    public List<Reservation> getAllUsersReservations(String login) throws AppBaseException {
        try {
            return reservationFacade.findByLogin(login);
        } catch (ReservationNotFoundException e) {
            throw new ReservationNotFoundException(e);
        }
    }

    /**
     * Gets status by name.
     *
     * @param statusName the status name
     * @return the status by name
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getStatusByName")
    public Status getStatusByName(String statusName) throws AppBaseException  {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets status cancelled.
     *
     * @return the status cancelled
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed({"getStatusCancelled", "cancelReservation"})
    public Status getStatusCancelled() throws AppBaseException {
        return statusFacade.findByStatusName(ReservationStatuses.cancelled.toString()).get();
    }

    /**
     * Gets all statuses.
     *
     * @return the all statuses
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getAllStatuses")
    public List<Status> getAllStatuses() throws AppBaseException {
        return statusFacade.findAll();
    }

    /**
     * Gets reservation by number.
     *
     * @param reservationNumber the reservation number
     * @return the reservation by number
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getReservationByNumber")
    public Reservation getReservationByNumber(String reservationNumber) throws AppBaseException {
        if(reservationFacade.findByNumber(reservationNumber).isPresent()) {
            return this.reservationFacade.findByNumber(reservationNumber).get();
        } else throw new AppBaseException("error.default");
    }

    /**
     * Change reservation status.
     *
     * @param reservation the reservation
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("changeReservationStatus")
    public void changeReservationStatus(Reservation reservation) throws AppBaseException {
        throw new UnsupportedOperationException();
    }


    /**
     * Cancel reservation.
     *
     * @param reservation the reservation
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("cancelReservation")
    public void cancelReservation(Reservation reservation) throws AppBaseException {
        reservationFacade.edit(reservation);
    }

    /**
     * Gets reservations by date.
     *
     * @param localDateTime the local date time
     * @return the reservations by date
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getReservationsByDate")
    public List<Reservation> getReservationsByDate(LocalDateTime localDateTime) throws AppBaseException  {
        throw new UnsupportedOperationException();
    }

    /**
     * Edit reservation.
     *
     * @param reservation the reservation
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("editReservation")
    public void editReservation(Reservation reservation) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Filter reservations list.
     *
     * @param filter the filter
     * @return the list
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("filterReservations")
    public List<Reservation> filterReservations(String filter) throws AppBaseException {
        return reservationFacade.filterReservations(filter);
    }

    /**
     * Gets user reviewable reservations.
     *
     * @param login the login
     * @return the user reviewable reservations
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getUserReviewableReservations")
    public List<Reservation> getUserReviewableReservations(String login) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

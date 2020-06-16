package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ExtraServiceNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReservationNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReviewNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.StatusNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private ReviewFacade reviewFacade;

    @Inject
    private HallFacade hallFacade;

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private  ExtraServiceFacade extraServiceFacade;

    @Inject
    private ClientFacade clientFacade;


    /**
     * Pobieranie listy wszystkich rezerwacji
     *
     * @return List<Reservation> all reservations
     * @throws AppBaseException podstawowy wyjatek aplikacyjny
     */
    @RolesAllowed("getAllReservations")
    public List<Reservation> getAllReservations() throws AppBaseException {
        return reservationFacade.findAll();
    }

    /**
     * Gets client by login.
     *
     * @param login the login
     * @return the client by login
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("findByLogin")
    public Client getClientByLogin(String login) throws AppBaseException{
        return clientFacade.findByLogin(login);
    }

    /**
     * Gets extra service by name.
     *
     * @param name the name
     * @return the extra service by name
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getExtraServiceByName")
    public ExtraService getExtraServiceByName(String name) throws AppBaseException{
        if(extraServiceFacade.findByName(name).isEmpty()){
            throw new ExtraServiceNotFoundException();
        }else return extraServiceFacade.findByName(name).get();
    }

    /**
     * Gets event type by name.
     *
     * @param name the name
     * @return the event type by name
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getEventTypeByName")
    public EventType getEventTypeByName(String name) throws AppBaseException{
        if(eventTypesFacade.findByName(name).isEmpty()){
            throw new ExtraServiceNotFoundException();
        }else return eventTypesFacade.findByName(name).get();
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

    /**
     * Gets hall by name.
     *
     * @param name the name
     * @return the hall by name
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed({"getHallByName", "getHallForReservation"})
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

    /**
     * Gets all users reservations.
     *
     * @param login the login
     * @return the all users reservations
     * @throws AppBaseException the app base exception
     */
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
        if(statusFacade.findByStatusName(statusName).isPresent()) {
            return statusFacade.findByStatusName(statusName).get();
        } else {
            throw new StatusNotFoundException();
        }
    }

    /**
     * Gets status cancelled.
     *
     * @return the status cancelled
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed({"getStatusCancelled", "cancelReservation"})
    public Status getStatusCancelled() throws AppBaseException {
        if(statusFacade.findByStatusName(ReservationStatuses.cancelled.toString()).isPresent()) {
            return statusFacade.findByStatusName(ReservationStatuses.cancelled.toString()).get();
        } else {
            throw new StatusNotFoundException();
        }
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
        } else throw new ReservationNotFoundException();
    }

    /**
     * Change reservation status.
     *
     * @param reservation the reservation
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("changeReservationStatus")
    public void changeReservationStatus(Reservation reservation) throws AppBaseException {
        reservationFacade.edit(reservation);
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
        reservationFacade.edit(reservation);
    }


    /**
     * Filter reservations list.
     *
     * @pram filter the filter
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
        List<Reservation> result;
        try {
            List<Reservation> userFinishedReservations = reservationFacade
                    .findByLogin(login)
                    .stream()
                    .filter(reservation -> reservation.getStatus().getStatusName().equals(ReservationStatuses.finished.name()))
                    .collect(Collectors.toList());
            List<Reservation> reviewedReservations = reviewFacade
                    .findByLogin(login)
                    .stream()
                    .map(Review::getReservation)
                    .collect(Collectors.toList());
            result = new ArrayList<>(userFinishedReservations);
            result.removeAll(reviewedReservations);
        } catch (ReservationNotFoundException e) {
            throw new ReservationNotFoundException(e);
        } catch (ReviewNotFoundException e){
            throw new ReviewNotFoundException(e);
        }
        return result;
    }


    public List<ExtraService> getAllExtraServices() throws AppBaseException{
       return extraServiceFacade.findAll();
    }

    @RolesAllowed("getExtraServiceByName")
    public ExtraService getExtraServicesByName(String name) throws AppBaseException{
        if(extraServiceFacade.findByName(name).isPresent())
        return extraServiceFacade.findByName(name).get();
        else throw new ExtraServiceNotFoundException();
    }
}

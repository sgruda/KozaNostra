package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReservationNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.List;

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

    @RolesAllowed("getAllReservations")
    public List<Reservation> getAllReservations() throws AppBaseException {
        return reservationFacade.findAll();
    }

    @RolesAllowed("getAllEventTypes")
    public List<EventType> getAllEventTypes() throws AppBaseException {
        return eventTypesFacade.findAll();
    }

    @RolesAllowed("createReservation")
    public void createReservation(Reservation reservation) throws AppBaseException {
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getAllUsersReservations")
    public List<Reservation> getAllUsersReservations(String login) throws AppBaseException {
        try {
            return reservationFacade.findByLogin(login);
        } catch (ReservationNotFoundException e) {
            throw new ReservationNotFoundException(e);
        }
    }
    @RolesAllowed("getStatusByName")
    public Status getStatusByName(String statusName) throws AppBaseException  {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getReservationByNumber")
    public Reservation getReservationByNumber(String reservationNumber) throws AppBaseException {
        if(reservationFacade.findByNumber(reservationNumber).isPresent()) {
            return this.reservationFacade.findByNumber(reservationNumber).get();
        } else throw new AppBaseException("error.default");
    }

    @RolesAllowed("changeReservationStatus")
    public void changeReservationStatus(Reservation reservation) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getStatusCanceled")
    public Status getStatusCanceled() throws AppBaseException  {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("cancelReservation")
    public void cancelReservation(Reservation reservation) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getReservationsByDate")
    public List<Reservation> getReservationsByDate(LocalDateTime localDateTime) throws AppBaseException  {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("editReservation")
    public void editReservation(Reservation reservation) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("filterReservations")
    public List<Reservation> filterReservations(String filter) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getUserReviewableReservations")
    public List<Reservation> getUserReviewableReservations(String login) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

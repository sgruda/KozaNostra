package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.EventTypesFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ReservationFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.StatusFacade;

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

    @RolesAllowed("getAllReservations")
    public List<Reservation> getAllReservations(){
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getAllEventTypes")
    public List<EventType> getAllEventTypes(){
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("createReservation")
    public void createReservation(Reservation reservation){
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getAllUsersReservations")
    public List<Reservation> getAllUsersReservations(String login){
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }
    @RolesAllowed("getStatusByName")
    public Status getStatusByName(String statusName) {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getReservationByNumber")
    public Reservation getReservationByNumber(String reservationNumber) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("changeReservationStatus")
    public void changeReservationStatus(Reservation reservation) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getStatusCanceled")
    public Status getStatusCanceled() {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("cancelReservation")
    public void cancelReservation(Reservation reservation) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getReservationsByDate")
    public List<Reservation> getReservationsByDate(LocalDateTime localDateTime){
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("editReservation")
    public void editReservation(Reservation reservation){
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

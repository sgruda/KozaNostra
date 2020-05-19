package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;


import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.EventTypesFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ExtraServiceFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ReservationFacade;

import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ReservationManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ReservationFacade reservationFacade;

    @Inject
    private EventTypesFacade eventTypesFacade;

    public List<Reservation> getAllReservations(){
        //TODO Implementacja
        return new ArrayList<>();
    }

    public List<EventType> getAllEventTypes(){
        //TODO Implementacja
        return new ArrayList<>();
    }

    public void createReservation(Reservation reservation){
        //TODO Implementacja
    }

    public List<Reservation> getAllUsersReservations(String login){
        //TODO Implementacja
        return new ArrayList<>();
    }

    public Reservation getReservationByNumber(String number){
        return null;
    }

    public List<Reservation> getReservationsByDate(Date date){
        return null;
    }

    public void editReservation(Reservation reservation){
        throw new UnsupportedOperationException();
    }

}

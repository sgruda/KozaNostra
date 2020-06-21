package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.*;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ExtraServiceNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReservationNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReviewNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.StatusNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa odpowiedzialna za operacje na obiektach encyjnych typu Reservation
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
    private  ExtraServiceFacade extraServiceFacade;

    @Inject
    private ClientFacade clientFacade;

    @Inject
    private AverageGuestNumberFacade averageGuestNumberFacade;
    private AverageGuestNumber aggregate;

    /**
     * Metoda odpowiedzialna za pobranie listy wszystkich rezerwacji
     *
     * @return List<Reservation>  lista rezerwacji
     * @throws AppBaseException podstawowy wyjatek aplikacyjny
     */
    @RolesAllowed("getAllReservations")
    public List<Reservation> getAllReservations() throws AppBaseException {
        return reservationFacade.findAll();
    }

    /**
     * Metoda odpowiedzialna za pobranie użytkownika o poziomie dostępu Klient
     *
     * @param login login użytkownika
     * @return konto klienta
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("findByLogin")
    public Client getClientByLogin(String login) throws AppBaseException{
        return clientFacade.findByLogin(login);
    }

    /**
     * Metoda odpowiedzialna za pobranie usługi dodatkowej po nazwie
     *
     * @param name nazwa usługi dodatkowej
     * @return Obiekt typu ExtraService
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getExtraServiceByName")
    public ExtraService getExtraServiceByName(String name) throws AppBaseException{
        if(extraServiceFacade.findByName(name).isEmpty()){
            throw new ExtraServiceNotFoundException();
        }else return extraServiceFacade.findByName(name).get();
    }

    /**
     * Metoda odpowiedzialna za pobranie typu wydarzenia po nazwie
     *
     * @param name nazwa typu wydarzenia
     * @return Obiekt typu EventType
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getEventTypeByName")
    public EventType getEventTypeByName(String name) throws AppBaseException{
        if(eventTypesFacade.findByName(name).isEmpty()){
            throw new ExtraServiceNotFoundException();
        }else return eventTypesFacade.findByName(name).get();
    }

    /**
     * Metoda odpowiedzialna za pobranie sali
     *
     * @param name nazwa sali
     * @return Obiekt typu Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
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
     * Metoda odpowiedzialna za pobranie wszystkich rezerwacji użytkownika
     *
     * @param login login użytkownika
     * @return lista rezerwacji użytkownika
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
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
     * Metoda odpowiedzialna za pobieranie statusu na podstawie jego nazwy.
     *
     * @param statusName Nazwa statusu
     * @return Obiekt typu Status.
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
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
     * Metoda odpowiedzialna za pobieranie statusu "anulowana".
     *
     * @return Obiekt typu Status.
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
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
     * Metoda odpowiedzialna za pobieranie listy wszystkich statusów rezerwacji.
     *
     * @return lista obiektów typu Status
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getAllStatuses")
    public List<Status> getAllStatuses() throws AppBaseException {
        return statusFacade.findAll();
    }

    /**
     * Metoda odpowiedzialna za pobieranie rezerwacji na podstawie jej numeru.
     *
     * @param reservationNumber Numer rezerwacji
     * @return Obiekt typu Reservation reprezentujący rezerwację
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getReservationByNumber")
    public Reservation getReservationByNumber(String reservationNumber) throws AppBaseException {
        if(reservationFacade.findByNumber(reservationNumber).isPresent()) {
            return this.reservationFacade.findByNumber(reservationNumber).get();
        } else throw new ReservationNotFoundException();
    }

    /**
     * Metoda odpowiedzialna za zmianę statusu rezerwacji.
     *
     * @param reservation Obiekt typu Reservation
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("changeReservationStatus")
    public void changeReservationStatus(Reservation reservation) throws AppBaseException {
        reservationFacade.edit(reservation);
        if(reservation.getStatus().getStatusName().equalsIgnoreCase(ReservationStatuses.finished.toString())) {
            aggregate = averageGuestNumberFacade.findAll().get(0);
            aggregate.setEventSum(aggregate.getEventSum() + 1);
            aggregate.setGuestSum(aggregate.getGuestSum() + reservation.getGuestsNumber());
            aggregate.setAverage(aggregate.getGuestSum() / aggregate.getEventSum());
            averageGuestNumberFacade.edit(aggregate);
        }
    }

    /**
     * Metoda odpowiedzialna za anulowanie rezerwacji przez użytkownika o poziomie dostępu klient.
     *
     * @param reservation Obiekt typu Reservation
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("cancelReservation")
    public void cancelReservation(Reservation reservation) throws AppBaseException {
        reservationFacade.edit(reservation);
    }

    /**
     * Metoda odpowiedzialna za edycję rezerwacji
     *
     * @param reservation rezerwacja
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("editReservation")
    public void editReservation(Reservation reservation) throws AppBaseException {
        reservationFacade.edit(reservation);
    }


    /**
     * Metoda odpowiedzialna za filtrowanie listy z rezerwacjami
     *
     * @param filter ciąg znaków, filtr
     * @return lista rezerwacji zgodna z filtrem
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("filterReservations")
    public List<Reservation> filterReservations(String filter) throws AppBaseException {
        return reservationFacade.filterReservations(filter);
    }

    /**
     * Metoda zwracająca listę rezerwacji dla których możliwe jest dodanie opinii przez konto z podaną nazwą użytkownika.
     * Wystawienie opinii jest możliwe dla zakończonych rezerwacji, dla których nie została wystawiona opinia.
     *
     * @param login nazwa użytkownika
     * @return lista rezerwacji dla których możliwe jest wystawienie opinii
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
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


    /**
     * Metoda odpowiedzialna za pobranie wszystkich usług dodatkowych
     *
     * @return lista wszystkich usług dodatkowych
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getAllExtraServicesForReservation")
    public List<ExtraService> getAllExtraServices() throws AppBaseException{
       return extraServiceFacade.findAll();
    }

    /**
     * Metoda odpowiedzialna za pobranie usługi dodatkowej po nazwie
     *
     * @param name nazwa usługi dodatkowej
     * @return Obiekt typu ExtraService
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getExtraServiceByName")
    public ExtraService getExtraServicesByName(String name) throws AppBaseException{
        if(extraServiceFacade.findByName(name).isPresent())
            return extraServiceFacade.findByName(name).get();
        else throw new ExtraServiceNotFoundException();
    }

    /**
     * Metoda odpowiedzialna za pobranie obiektu agregatu
     *
     * @return Obiekt typu AverageGuestNumber
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    @PermitAll
    public AverageGuestNumber getAggregate() throws AppBaseException {
        return averageGuestNumberFacade.findAll().get(0);
    }
}

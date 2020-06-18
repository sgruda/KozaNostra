package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ExtraServiceMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReservationMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.EventTypeMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Punkt dostępowy implementujący interfejs EditReservationEndpointLocal
 * pośredniczący przy edycji rezerwacji
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class EditReservationEndpoint implements Serializable, EditReservationEndpointLocal {
    @Inject
    private ReservationManager reservationManager;
    private Reservation reservation;

    @Getter
    private Collection<String> eventTypes;

    @Getter
    private Hall hall;

    @Override
    @RolesAllowed("getReservationByNumber")
    public ReservationDTO getReservationByNumber(String number) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reservation = reservationManager.getReservationByNumber(number);
                rollback = reservationManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + reservationManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return ReservationMapper.INSTANCE.toReservationDTO(reservation);
    }


    @Override
    @RolesAllowed("editReservation")
    public void editReservation(ReservationDTO reservationDTO) throws AppBaseException{
        ReservationMapper.INSTANCE.updateReservationFromDTO(reservationDTO, reservation);
        reservation.setEventType(reservationManager.getEventTypeByName(reservationDTO.getEventTypeName()));
        List<ExtraService> extraServices = new ArrayList<>();
        for(String extraService: reservationDTO.getExtraServiceCollection()){
            extraServices.add(reservationManager.getExtraServicesByName(extraService));
        }
        reservation.setExtra_service(extraServices);
        reservation.setTotalPrice(calculateTotalPrice());
        reservationManager.editReservation(reservation);
    }

    @Override
    @RolesAllowed("GetHallForReservation")
    public HallDTO getHallByName(String name) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                 hall = reservationManager.getHallByName(name);
                eventTypes = EventTypeMapper.toEventTypeStringCollection(hall.getEvent_type());
                rollback = reservationManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + reservationManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return HallMapper.INSTANCE.toHallDTO(hall);
    }

    @Override
    @RolesAllowed("getAllExtraServicesForReservation")
    public List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException{
        List<ExtraServiceDTO> extraServices = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                extraServices = ExtraServiceMapper.INSTANCE.toExtraServiceDTOList(reservationManager.getAllExtraServices());
                rollback = reservationManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + reservationManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        Collection<ExtraServiceDTO> extraServicesActiveList = new ArrayList<>();
        for (ExtraServiceDTO ext : extraServices) {
            if (ext.isActive()) {
                extraServicesActiveList.add(ext);
            }
        }
        return new ArrayList<>(extraServicesActiveList);
    }

    @Override
    @RolesAllowed("getUnavailableDates")
    public List<UnavailableDate> getUnavailableDates(String hallName) throws AppBaseException {
        Collection<ReservationDTO> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                list = ReservationMapper.INSTANCE.toReservationDTOCollection(reservationManager.getAllReservations());
                rollback = reservationManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if (callCounter > 0)
                log.info("Transaction with ID " + reservationManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }

        List<UnavailableDate> dates = new ArrayList<>();
        for (ReservationDTO res : list) {
            if(res.getHallName().equalsIgnoreCase(hallName)){
                dates.add(new UnavailableDate(LocalDateTime.parse(res.getStartDate(), DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" )),
                        LocalDateTime.parse(res.getEndDate(),DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ))));
            }
        }
        return new ArrayList<>(dates);
    }
    
    private double calculateTotalPrice() {
        Period period = DateFormatter.getPeriod(reservation.getStartDate(),reservation.getEndDate());
        int rentedTime = period.getDays();
        long[] time = DateFormatter.getTime(reservation.getStartDate(),reservation.getEndDate());
        if(time[2]>0)
            rentedTime+=1;
        double totalPrice = hall.getPrice() * rentedTime * reservation.getGuestsNumber();
        for (ExtraService ext : this.reservation.getExtra_service()) {
           totalPrice+=ext.getPrice();
        }
        return totalPrice;
    }
}

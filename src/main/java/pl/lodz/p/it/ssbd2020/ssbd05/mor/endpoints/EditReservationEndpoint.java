package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ExtraServiceMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReservationMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.EventTypeMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ExtraServiceManager;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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

    @Inject
    private ExtraServiceManager extraServiceManager;

    @Getter
    private Hall hall;

    @Getter
    @Setter
    private List<ExtraService> extraServiceList;

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
        reservation.setStatus(reservationManager.getStatusByName(ReservationStatuses.submitted.name()));
        reservation.setEventType(reservationManager.getEventTypeByName(reservationDTO.getEventTypeName()));
        List<ExtraService> extraServices = new ArrayList<>();

        reservation.setExtra_service(extraServices);

        double extraServicesTotalPrice = 0;
        for (String extraService : reservationDTO.getExtraServiceCollection()) {
            for(ExtraService extra : this.extraServiceList){
                if(extra.getServiceName().equalsIgnoreCase(extraService)){
                    extraServicesTotalPrice += extra.getPrice();
                    extraServices.add(extra);
                }
            }
        }
        reservation.setTotalPrice(calculateTotalPrice(reservation.getStartDate(),reservation.getEndDate(),hall.getPrice(),reservation.getGuestsNumber(),extraServicesTotalPrice));

        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reservationManager.editReservation(reservation);
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
                this.extraServiceList = extraServiceManager.getAllExtraServices();
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

    public double calculateTotalPrice(LocalDateTime startDate,LocalDateTime endDate,double hallPrice,
                                      Long numberOfGuests, double extraServicesTotalPrice) {
        long rentedTime = DateFormatter.getHours(startDate,endDate);
        double price;
        price = hallPrice * rentedTime * numberOfGuests;
        price += extraServicesTotalPrice;
        price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return price;
    }
}

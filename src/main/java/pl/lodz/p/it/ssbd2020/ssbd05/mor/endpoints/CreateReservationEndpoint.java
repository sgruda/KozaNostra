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
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.CreateReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ExtraServiceManager;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Punkt dostępowy implementujący interfejs CreateReservationEndpointLocal
 * pośredniczący w tworzeniu nowej rezerwacji
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class CreateReservationEndpoint implements Serializable, CreateReservationEndpointLocal {
    @Inject
    private ReservationManager reservationManager;

    @Inject
    private ExtraServiceManager extraServiceManager;

    @Getter
    @Setter
    private Hall hall;

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
            if (res.getStatusName().equalsIgnoreCase(ReservationStatuses.cancelled.name())) {
                if (res.getHallName().equalsIgnoreCase(hallName)) {
                    dates.add(new UnavailableDate(LocalDateTime.parse(res.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            LocalDateTime.parse(res.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                }
            }
        }
        return new ArrayList<>(dates);
    }

    @Override
    @RolesAllowed("getHallByName")
    public HallDTO getHallByName(String hallName) throws AppBaseException {
        HallDTO hallDTO = new HallDTO();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                this.hall = reservationManager.getHallByName(hallName);
                hallDTO = HallMapper.INSTANCE.toHallDTO(hall);
                ExtraServiceMapper.INSTANCE.toExtraServiceDTOList(extraServiceManager.getAllExtraServices());

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
        return hallDTO;
    }

    @Override
    @RolesAllowed("getAllExtraServices")
    public List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException {
        Collection<ExtraServiceDTO> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                list = ExtraServiceMapper.INSTANCE.toExtraServiceDTOList(extraServiceManager.getAllExtraServices());
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
        Collection<ExtraServiceDTO> listTemp = new ArrayList<>();
        for (ExtraServiceDTO ext : list) {
            if (ext.isActive()) {
                listTemp.add(ext);
            }
        }
        return new ArrayList<>(listTemp);
    }

    @Override
    @RolesAllowed("createReservation")
    public void createReservation(ReservationDTO reservationDTO) throws AppBaseException {
        Reservation reservation = ReservationMapper.INSTANCE.createNewReservation(reservationDTO);
        reservation.setClient(reservationManager.getClientByLogin(reservationDTO.getClientDTO().getLogin()));

        List<ExtraService> selectedExtraService = new ArrayList<>();

        double extraServicesTotalPrice = 0;
        for (String extraService : reservationDTO.getExtraServiceCollection()) {
            ExtraService extra = reservationManager.getExtraServiceByName(extraService);
            extraServicesTotalPrice += extra.getPrice();
            selectedExtraService.add(extra);
        }
        if (selectedExtraService.size() > 0) {
            reservation.setExtra_service(selectedExtraService);
        }
        reservation.setEventType(reservationManager.getEventTypeByName(reservationDTO.getEventTypeName()));
        reservation.setGuestsNumber(reservationDTO.getGuestsNumber());
        reservation.setStatus(reservationManager.getStatusByName(reservationDTO.getStatusName()));


        reservation.setHall(hall);
        reservation.setTotalPrice(calculateTotalPrice(reservation.getStartDate(), reservation.getEndDate(), hall.getPrice(), reservation.getGuestsNumber(), extraServicesTotalPrice));
        reservation.setReservationNumber(reservationDTO.getReservationNumber());


        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reservationManager.createReservation(reservation);
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
    @RolesAllowed("createReservation")
    public double calculateTotalPrice(LocalDateTime startDate, LocalDateTime endDate, double hallPrice,
                                      Long numberOfGuests, double extraServicesTotalPrice) {
        long rentedTime = DateFormatter.getHours(startDate, endDate);
        double price;
        price = hallPrice * rentedTime * numberOfGuests;
        price += extraServicesTotalPrice;
        price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return price;
    }
}

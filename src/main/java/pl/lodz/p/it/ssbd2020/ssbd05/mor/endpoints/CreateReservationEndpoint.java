package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import jdk.dynalink.beans.StaticClass;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ExtraServiceMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReservationMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.EventTypeMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.CreateReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ExtraServiceManager;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.managers.HallManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.formatDate;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class CreateReservationEndpoint implements Serializable, CreateReservationEndpointLocal {
    @Inject
    private ReservationManager reservationManager;

    @Inject
    private ExtraServiceManager extraServiceManager;

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
                dates.add(new UnavailableDate(LocalDateTime.parse(res.getStartDate(),DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" )),
                        LocalDateTime.parse(res.getEndDate(),DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ))));
            }
        }
        return new ArrayList<>(dates);
    }

    @Override
    @RolesAllowed("getAllEventTypes")
    public List<String> getAllEventTypes() throws AppBaseException {
        Collection<String> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                list = EventTypeMapper.toEventTypeStringCollection(reservationManager.getAllEventTypes());
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
        return new ArrayList<>(list);
    }

    @Override
    @RolesAllowed("getHallByName")
    public HallDTO getHallByName(String hallName) throws AppBaseException {
        HallDTO hallDTO = new HallDTO();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                hallDTO = HallMapper.INSTANCE.toHallDTO(reservationManager.getHallByName(hallName));
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
        for (String extraService : reservationDTO.getExtraServiceCollection()) {
            selectedExtraService.add(reservationManager.getExtraServiceByName(extraService));
        }
        reservation.setExtra_service(selectedExtraService);
        reservation.setEventType(reservationManager.getEventTypeByName(reservationDTO.getEventTypeName()));
        reservation.setGuestsNumber(reservationDTO.getGuestsNumber());
        reservation.setStatus(reservationManager.getStatusByName(reservationDTO.getStatusName()));
        reservation.setHall(reservationManager.getHallByName(reservationDTO.getHallName()));
        reservation.setTotalPrice(reservationDTO.getTotalPrice());
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
}

package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReservationMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.NoncancelableReservationException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ChangeReservationStatusEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ChangeReservationStatusEndpoint implements Serializable, ChangeReservationStatusEndpointLocal {
    @Inject
    private ReservationManager reservationManager;
    private Reservation reservation;

//    @Override
//    @RolesAllowed("getStatusByName")
//    public StatusDTO getStatusByName(String statusName) {
//        throw new UnsupportedOperationException();
//    }

    @Override
    @RolesAllowed("getAllStatuses")
    public List<String> getAllStatuses() throws AppBaseException {
        return reservationManager.getAllStatuses()
                .stream()
                .map(Status::getStatusName)
                .collect(Collectors.toList());
    }

    @Override
    @RolesAllowed("getReservationByNumber")
    public ReservationDTO getReservationByNumber(String reservationNumber) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reservation = reservationManager.getReservationByNumber(reservationNumber);
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
    @RolesAllowed("changeReservationStatus")
    public void changeReservationStatus(ReservationDTO reservationDTO) throws AppBaseException {
log.severe("WTF 1");
        reservation.setStatus(reservationManager.getStatusByName(reservationDTO.getStatusName()));
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reservationManager.changeReservationStatus(reservation);
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
    }

    @Override
    @RolesAllowed("cancelReservation")
    public void cancelReservation(ReservationDTO reservationDTO) throws AppBaseException {
        if(!reservation.getStatus().getStatusName().equalsIgnoreCase(ReservationStatuses.submitted.toString()))
            throw new NoncancelableReservationException();
        reservation.setStatus(reservationManager.getStatusCancelled());
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                reservationManager.cancelReservation(reservation);
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
    }
}

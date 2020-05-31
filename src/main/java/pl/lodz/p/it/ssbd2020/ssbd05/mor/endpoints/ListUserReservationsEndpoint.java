package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReservationMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListUserReservationsEndpointLocal;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ListUserReservationsEndpoint implements Serializable, ListUserReservationsEndpointLocal {

    @Inject
    private ReservationManager reservationManager;

    @Override
    @RolesAllowed("getAllUsersReservations")
    public List<ReservationDTO> getAllUsersReservations(String login) throws AppBaseException {
        Collection<ReservationDTO> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                for (Reservation res:reservationManager.getAllUsersReservations(login)) {
                    ReservationDTO dto = new ReservationDTO();
                    dto.setClientLogin(res.getClient().getAccount().getLogin());
                    dto.setEndDate(res.getEndDate().toString());
                    dto.setStartDate(res.getStartDate().toString());
                    dto.setReservationNumber(res.getReservationNumber());
                    dto.setEventTypeName(res.getEventType().getTypeName());
                    dto.setTotalPrice(res.getTotalPrice());
                    dto.setReviewNumber("1");
                    dto.setHallName(res.getHall().getName());
                    dto.setStatusName(res.getStatus().getStatusName());
                    Collection<String> extraService = new ArrayList<>();
                    for (ExtraService extra:res.getExtraServiceCollection()){
                        extraService.add(extra.getServiceName());
                    }
                    dto.setExtraServiceCollection(extraService);
                    list.add(dto);
                }
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
        return (List<ReservationDTO>) list;
    }

    @Override
    @RolesAllowed("getUserReviewableReservations")
    public List<ReservationDTO> getUserReviewableReservations(String login) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

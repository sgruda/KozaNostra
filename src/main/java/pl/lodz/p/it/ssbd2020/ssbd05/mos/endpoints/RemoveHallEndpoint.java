package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallHasReservationsException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.RemoveHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.managers.HallManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class RemoveHallEndpoint implements Serializable, RemoveHallEndpointLocal {

    @Inject
    private HallManager hallManager;

    @Getter
    @Setter
    private Hall hall;


    @Override
    @RolesAllowed("removeHall")
    public void removeHall(HallDTO hallDTO) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                HallMapper.INSTANCE.updateHallFromDTO(hallDTO,hall);
                rollback = hallManager.isLastTransactionRollback();
                log.severe("nr sali" + hall.getId());
                if (hall.getReservationCollection().isEmpty()) {
                    hallManager.removeHall(hall);
                } else throw new HallHasReservationsException();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if (callCounter > 0)
                log.info("Transaction with ID " + hallManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @Override
    public HallDTO getHallByName(String hallName) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                hall = hallManager.getHallByName(hallName);
                log.severe("nr sali 2"+ hall.getId());
                rollback = hallManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if (callCounter > 0)
                log.info("Transaction with ID " + hallManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return HallMapper.INSTANCE.toHallDTO(hall);
    }


}

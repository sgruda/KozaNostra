package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.HallDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.managers.HallManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collection;

/**
 * Punkt dostępowy implementujący interfejs HallDetailsEndpointLocal, który pośredniczy
 * przy wyświetlaniu szczegółów wybranej sali
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class HallDetailsEndpoint implements Serializable, HallDetailsEndpointLocal {

    @Inject
    private HallManager hallManager;

    @Getter
    @Setter
    private Hall hall;

    @Getter
    @Setter
    private Collection<EventType> eventTypes;

    @Override
    @PermitAll
    public HallDTO getHallByName(String name) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                this.hall = hallManager.getHallByName(name);
                rollback = hallManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + hallManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return HallMapper.INSTANCE.toHallDTO(hall);
    }

   @Override
    @RolesAllowed("changeHallActivity")
    public void changeActivity(HallDTO hallDTO) throws AppBaseException {
        Address temp = hall.getAddress();
        HallMapper.INSTANCE.updateHallFromDTO(hallDTO, hall);
        eventTypes = hallManager.getAllEventTypes();
        eventTypes.removeIf(eventType -> !hallDTO.getEvent_type().contains(eventType.getTypeName()));
        hall.setEvent_type(eventTypes);
        hall.setAddress(temp);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                hallManager.changeActivity(hall);
                rollback = hallManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + hallManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}

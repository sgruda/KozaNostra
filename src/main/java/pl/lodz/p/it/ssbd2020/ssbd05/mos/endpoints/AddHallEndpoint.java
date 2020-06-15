package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.AddressMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.EventTypeMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.AddHallEndpointLocal;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Punkt dostępowy implementujący interfejs AddHallEndpointLocal, który pośredniczy
 * przy dodawaniu sali przez użytkownika o poziomie dostępu menadżer
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class AddHallEndpoint implements Serializable, AddHallEndpointLocal {

    @Inject
    private HallManager hallManager;
    private Hall hall;
    private Collection<EventType> eventTypes;

    @Override
    @RolesAllowed("addHall")
    public void addHall(HallDTO hallDTO) throws AppBaseException {
        hall = HallMapper.INSTANCE.createNewHall(hallDTO);
        eventTypes.removeIf(eventType -> !hallDTO.getEvent_type().contains(eventType.getTypeName()));
        hall.setEvent_type(eventTypes);
        hall.setAddress(AddressMapper.INSTANCE.createNewAddress(hallDTO.getAddress()));
        hall.setReservationCollection(new ArrayList<>());
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                hallManager.addHall(hall);
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

    @Override
    @RolesAllowed("getAllEventTypes")
    public List<String> getAllEventTypes() throws AppBaseException {
        List<String> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                eventTypes = hallManager.getAllEventTypes();
                list.addAll(EventTypeMapper.toEventTypeStringCollection(eventTypes));
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
        return list;
    }

    @Override
    @RolesAllowed("getAllAddresses")
    public List<AddressDTO> getAllAddresses() throws AppBaseException {
        List<AddressDTO> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                list.addAll(AddressMapper.INSTANCE.toAddressDTOCollection(hallManager.getAllAddresses()));
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
        return list;
    }
}

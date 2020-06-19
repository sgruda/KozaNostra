package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.EventTypeMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallActiveException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.EditHallEndpointLocal;
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
 * Punkt dostępowy implementujący interfejs EditHallEndpointLocal, który pośredniczy
 * przy edycji sali przez użytkownika o poziomie dostępu menadżer
 */
@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class EditHallEndpoint implements Serializable, EditHallEndpointLocal {

    @Inject
    private HallManager hallManager;

    @Getter
    @Setter
    private Hall hall;

    @Getter
    @Setter
    private Collection<EventType> eventTypes;

    @Override
    @RolesAllowed("getHallByName")
    public HallDTO getHallByName(String name) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                this.hall = hallManager.getHallByName(name);
                if (this.hall.isActive()) {
                    throw new HallActiveException();
                }
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
    @RolesAllowed("editHall")
    public void editHall(HallDTO hallDTO) throws AppBaseException {
        Address temp = hall.getAddress();
        HallMapper.INSTANCE.updateHallFromDTO(hallDTO, hall);
        eventTypes.removeIf(eventType -> !hallDTO.getEvent_type().contains(eventType.getTypeName()));
        hall.setEvent_type(eventTypes);
        hall.setAddress(temp);
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                hallManager.editHall(hall);
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




}

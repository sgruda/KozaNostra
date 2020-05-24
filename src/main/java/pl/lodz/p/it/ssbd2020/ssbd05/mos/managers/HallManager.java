package pl.lodz.p.it.ssbd2020.ssbd05.mos.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.EventTypesFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.HallFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.facades.AddressFacade;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(TrackerInterceptor.class)
public class HallManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private HallFacade hallFacade;
    @Inject
    private AddressFacade addressFacade;
    @Inject
    private EventTypesFacade eventTypesFacade;

    @RolesAllowed("addHall")
    public void addHall(Hall hall) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @PermitAll
    public Hall getHallByName(String name) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @PermitAll
    public Collection<Hall> getAllHalls() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @PermitAll
    public Collection<Hall> getFilteredHalls(String hallFilter) throws AppBaseException {
        // TODO implement
        return new ArrayList<>();
    }

    @RolesAllowed("editHall")
    public void editHall(Hall hall) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getAllEventTypes")
    public List<EventType> getAllEventTypes() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("getAllAddresses")
    public List<Address> getAllAddresses() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("removeHall")
    public void removeHall(Hall hall) throws AppBaseException{
        throw new UnsupportedOperationException();
    }

    public void changeActivity(Hall hall) throws AppBaseException{
        throw new UnsupportedOperationException();
    }
}

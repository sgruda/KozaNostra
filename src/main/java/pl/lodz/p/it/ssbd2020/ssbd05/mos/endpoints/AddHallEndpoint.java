package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.AddHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.managers.HallManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class AddHallEndpoint implements Serializable, AddHallEndpointLocal {
    @Inject
    private HallManager hallManager;
    private Hall hall;

    @Override
    @RolesAllowed("addHall")
    public void addHall(HallDTO hallDTO) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("getAllEventTypes")
    public List<EventTypeDTO> getAllEventTypes() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("getAllAddresses")
    public List<AddressDTO> getAllAddresses() throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

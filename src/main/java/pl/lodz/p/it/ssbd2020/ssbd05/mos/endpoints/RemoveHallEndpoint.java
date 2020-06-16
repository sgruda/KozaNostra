package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos.HallMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallHasReservationsException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.RemoveHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.managers.HallManager;

import javax.annotation.security.RolesAllowed;
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

    @Override
    @RolesAllowed("getHallByName")
    public HallDTO getHallByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("removeHall")
    public void removeHall(HallDTO hallDTO) throws AppBaseException {
            Hall hall = HallMapper.INSTANCE.toHall(hallDTO);
            hall = hallManager.getHallByName(hall.getName());
        if(hall.getReservationCollection().isEmpty()) {
            hallManager.removeHall(hall);
        } else throw new HallHasReservationsException();
    }
}

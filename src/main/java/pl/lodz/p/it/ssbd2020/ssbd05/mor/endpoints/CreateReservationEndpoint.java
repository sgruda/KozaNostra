package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.CreateReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class CreateReservationEndpoint implements Serializable,CreateReservationEndpointLocal {
    @Inject
    private ReservationManager reservationManager;
    @Override
    @RolesAllowed("getUnavailableDates")
    public List<Timestamp> getUnavailableDates() throws AppBaseException {
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("getAllEventTypes")
    public List<EventTypeDTO> getAllEventTypes() throws AppBaseException {
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("createReservation")
    public void createReservation(ReservationDTO reservationDTO) throws AppBaseException {
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }
}

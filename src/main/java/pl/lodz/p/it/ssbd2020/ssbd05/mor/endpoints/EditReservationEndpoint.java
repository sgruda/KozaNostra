package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class EditReservationEndpoint implements Serializable, EditReservationEndpointLocal {
    @Inject
    private ReservationManager reservationManager;
    private Reservation reservation;

    @Override
    @RolesAllowed("getReservationByNumber")
    public ReservationDTO getReservationByNumber(String number) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("getReservationsByDate")
    public List<ReservationDTO> getReservationsByDate(LocalDateTime date) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("getAllEventTypes")
    public List<EventTypeDTO> getAllEventTypes() {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("editReservation")
    public void editReservation(ReservationDTO reservationDTO) {
        throw new UnsupportedOperationException();
    }
}

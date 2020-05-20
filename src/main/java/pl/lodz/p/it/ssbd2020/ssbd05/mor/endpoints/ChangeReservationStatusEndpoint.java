package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.StatusDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ChangeReservationStatusEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ChangeReservationStatusEndpoint implements Serializable, ChangeReservationStatusEndpointLocal {
    @Inject
    private ReservationManager reservationManager;

    @Override
    @RolesAllowed("getStatusByName")
    public StatusDTO getStatusByName(String statusName) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("getStatusCanceled")
    public StatusDTO getStatusCanceled() {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("getReservationByNumber")
    public ReservationDTO getReservationByNumber(String reservationNumber) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("changeReservationStatus")
    public void changeReservationStatus(ReservationDTO reservationDTO) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("cancelReservation")
    public void cancelReservation(ReservationDTO reservationDTO) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

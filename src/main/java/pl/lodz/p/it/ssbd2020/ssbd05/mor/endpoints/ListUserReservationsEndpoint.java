package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListUserReservationsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ListUserReservationsEndpoint implements Serializable, ListUserReservationsEndpointLocal {

    @Inject
    private ReservationManager reservationManager;

    @Override
    public List<ReservationDTO> getAllUsersReservations(String login) throws AppBaseException {
        return new ArrayList<>();
    }

    @Override
    @RolesAllowed("addReview")
    public List<ReservationDTO> getUserReviewableReservations(String login) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

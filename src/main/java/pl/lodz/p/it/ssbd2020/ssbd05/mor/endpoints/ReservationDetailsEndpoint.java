package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ReservationDetailsEndpointLocal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;

@Stateful
@TransactionAttribute(value = TransactionAttributeType.NEVER)
public class ReservationDetailsEndpoint implements Serializable, ReservationDetailsEndpointLocal {
    @Override
    @RolesAllowed("showReservationDetails")
    public ReservationDTO getReservationByNumber(String nubmer) {
        return null;
    }
}

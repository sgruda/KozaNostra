package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Stateful
@TransactionAttribute(value = TransactionAttributeType.NEVER)
public class EditReservationEndpoint implements Serializable, EditReservationEndpointLocal {
    @Override
    public ReservationDTO getReservationByNumber(String number) {
        return null;
    }

    @Override
    public List<ReservationDTO> getReservationsByDate(Date date) {
        return null;
    }

    @Override
    public List<EventTypeDTO> getAllEventTypes() {
        return null;
    }

    @Override
    @RolesAllowed("editReservation")
    public void editReservation(ReservationDTO reservationDTO) {

    }
}

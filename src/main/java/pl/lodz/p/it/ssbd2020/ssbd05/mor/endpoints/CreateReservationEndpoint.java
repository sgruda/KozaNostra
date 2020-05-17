package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import jdk.jshell.spi.ExecutionControl;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.CreateReservationEndpointLocal;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class CreateReservationEndpoint implements Serializable,CreateReservationEndpointLocal {

    @Override
    public List<Timestamp> getUnavailableDates() throws AppBaseException {
        //TODO Implementacja
        return null;
    }

    @Override
    public List<EventTypeDTO> getAllEventTypes() throws AppBaseException {
        //TODO Implementacja
        return null;
    }

    @Override
    public void createReservation(ReservationDTO reservationDTO) throws AppBaseException {
        //TODO Implementacja
    }
}

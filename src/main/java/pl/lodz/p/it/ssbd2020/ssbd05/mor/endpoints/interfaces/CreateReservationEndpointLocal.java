package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.sql.Timestamp;
import java.util.List;

@Local
public interface CreateReservationEndpointLocal {

    List<Timestamp> getUnavailableDates() throws AppBaseException;
    List<EventTypeDTO> getAllEventTypes() throws AppBaseException;
    void createReservation(ReservationDTO reservationDTO) throws AppBaseException;

}

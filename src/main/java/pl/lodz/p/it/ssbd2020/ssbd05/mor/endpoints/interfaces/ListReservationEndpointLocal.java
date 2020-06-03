package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ListReservationEndpointLocal {
    List<ReservationDTO> getAllReservations() throws AppBaseException;
    List<ReservationDTO> filterReservations(String filter) throws AppBaseException;
    List<String> getAllEventTypes() throws AppBaseException;
    List<String> getAllStatuses() throws AppBaseException;
}

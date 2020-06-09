package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Local
public interface EditReservationEndpointLocal {

    public ReservationDTO getReservationByNumber(String number) throws AppBaseException;
    public List<ReservationDTO> getReservationsByDate(LocalDateTime date) throws AppBaseException;
    public List<String> getEventTypeForHall(String hallName) throws AppBaseException;
    public void editReservation(ReservationDTO reservationDTO) throws AppBaseException;
}

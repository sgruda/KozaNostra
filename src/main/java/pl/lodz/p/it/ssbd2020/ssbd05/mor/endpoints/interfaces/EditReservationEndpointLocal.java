package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EditReservationEndpointLocal {

    ReservationDTO getReservationByNumber(String number) throws AppBaseException;
    void editReservation(ReservationDTO reservationDTO) throws AppBaseException;
    HallDTO getHallByName(String name) throws AppBaseException;
    List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException;
    List<UnavailableDate> getUnavailableDates(String hallName) throws AppBaseException;
}

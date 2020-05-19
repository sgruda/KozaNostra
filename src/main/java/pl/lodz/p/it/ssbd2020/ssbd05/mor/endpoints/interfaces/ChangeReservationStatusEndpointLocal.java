package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.StatusDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

@Local
public interface ChangeReservationStatusEndpointLocal {
    StatusDTO getStatusByName(String statusName);
    StatusDTO getStatusCanceled();
    ReservationDTO getReservationByNumber(String reservationNumber) throws AppBaseException;
    void changeReservationStatus(ReservationDTO reservationDTO) throws AppBaseException;
    void cancelReservation(ReservationDTO reservationDTO) throws AppBaseException;
}

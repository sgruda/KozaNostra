package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ListUserReservationsEndpointLocal {
    List<ReservationDTO> getAllUsersReservations(String login) throws AppBaseException;
    List<ReservationDTO> getUserReviewableReservations(String login) throws AppBaseException;


}

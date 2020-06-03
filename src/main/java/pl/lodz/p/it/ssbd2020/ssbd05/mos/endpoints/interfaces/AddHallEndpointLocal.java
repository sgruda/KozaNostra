package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AddHallEndpointLocal {

    void addHall(HallDTO hallDTO) throws AppBaseException;
    List<String> getAllEventTypes() throws AppBaseException;
    List<AddressDTO> getAllAddresses() throws AppBaseException;
}

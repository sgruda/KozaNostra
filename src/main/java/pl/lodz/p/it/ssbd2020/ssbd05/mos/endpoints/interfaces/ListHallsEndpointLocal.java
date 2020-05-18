package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ListHallsEndpointLocal {

    List<HallDTO> getAllHalls() throws AppBaseException;
    List<HallDTO> getFilteredHalls(String hallFilter) throws AppBaseException;
}

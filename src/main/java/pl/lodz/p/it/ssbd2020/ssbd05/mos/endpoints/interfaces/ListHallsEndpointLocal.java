package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import java.util.List;

public interface ListHallsEndpointLocal {
    List<HallDTO> getFilteredHalls(String hallFilter) throws AppBaseException;
}

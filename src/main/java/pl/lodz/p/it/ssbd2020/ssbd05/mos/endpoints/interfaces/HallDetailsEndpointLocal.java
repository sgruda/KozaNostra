package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

@Local
public interface HallDetailsEndpointLocal {

    HallDTO getHallByName(String name) throws AppBaseException;
}

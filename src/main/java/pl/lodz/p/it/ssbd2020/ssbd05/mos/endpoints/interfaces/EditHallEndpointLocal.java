package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EditHallEndpointLocal {

    HallDTO getHallByName(String name) throws AppBaseException;
    void editHall(HallDTO hallDTO) throws AppBaseException;
    List<String> getAllEventTypes() throws AppBaseException;
    public void changeActivity(HallDTO hallDTO) throws AppBaseException;
}

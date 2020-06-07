package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

@Local
public interface EditExtraServiceEndpointLocal {

    ExtraServiceDTO getExtraServiceByName(String name) throws AppBaseException;
    void editExtraService(ExtraServiceDTO extraServiceDTO) throws AppBaseException;
}

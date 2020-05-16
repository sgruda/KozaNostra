package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import java.util.List;

public interface ListExtraServicesEndpointLocal {

    List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException;
}

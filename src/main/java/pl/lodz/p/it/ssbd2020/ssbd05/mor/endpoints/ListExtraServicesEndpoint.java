package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListExtraServicesEndpointLocal;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ListExtraServicesEndpoint implements Serializable, ListExtraServicesEndpointLocal {

    @Override
    public List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException {
        // TODO implementacja
        return new ArrayList<>();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListExtraServicesEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ExtraServiceManager;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ListExtraServicesEndpoint implements Serializable, ListExtraServicesEndpointLocal {
    @Inject
    private ExtraServiceManager extraServiceManager;

    @Override
    @RolesAllowed("getAllExtraServices")
    public List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException {
        // TODO implementacja
        throw new UnsupportedOperationException();
    }
}

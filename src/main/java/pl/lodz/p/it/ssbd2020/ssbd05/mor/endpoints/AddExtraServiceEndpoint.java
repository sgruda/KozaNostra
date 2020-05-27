package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.AddExtraServiceEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ExtraServiceManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class AddExtraServiceEndpoint implements Serializable, AddExtraServiceEndpointLocal {
    @Inject
    private ExtraServiceManager extraServiceManager;
    private ExtraService extraService;

    @Override
    @RolesAllowed("addExtraService")
    public void addExtraService(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        // TODO implementacja
        throw new UnsupportedOperationException();
    }
}

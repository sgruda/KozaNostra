package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditExtraServiceEndpointLocal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class EditExtraServiceEndpoint implements Serializable, EditExtraServiceEndpointLocal {

    @Override
    @RolesAllowed("getExtraServiceByName")
    public ExtraServiceDTO getExtraServiceByName(String name) throws AppBaseException {
        // TODO implementacja
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("changeActivity")
    public void changeActivity(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        // TODO implementacja
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("editExtraService")
    public void editExtraService(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }
}

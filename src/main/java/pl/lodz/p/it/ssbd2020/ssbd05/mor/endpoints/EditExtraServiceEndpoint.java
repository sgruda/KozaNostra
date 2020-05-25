package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditExtraServiceEndpointLocal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
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

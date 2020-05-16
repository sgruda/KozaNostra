package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditExtraServiceEndpointLocal;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Local
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class EditExtraServiceEndpoint implements Serializable, EditExtraServiceEndpointLocal {

    @Override
    public ExtraServiceDTO getExtraServiceByName(String name) throws AppBaseException {
        // TODO implementacja
        return null;
    }

    @Override
    public void changeActivity(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        // TODO implementacja
    }
}

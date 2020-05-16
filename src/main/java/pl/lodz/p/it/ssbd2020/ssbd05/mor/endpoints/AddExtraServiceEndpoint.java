package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.AddExtraServiceEndpointLocal;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.io.Serializable;

@Named
@TransactionAttribute(TransactionAttributeType.NEVER)
@Stateful
@Local
public class AddExtraServiceEndpoint implements Serializable, AddExtraServiceEndpointLocal {

    @Override
    public void addExtraService(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        // TODO implementacja
    }
}

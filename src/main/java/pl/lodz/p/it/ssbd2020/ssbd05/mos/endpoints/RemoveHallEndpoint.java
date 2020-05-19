package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.RemoveHallEndpointLocal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;

@Stateful
@TransactionAttribute(value = TransactionAttributeType.NEVER)
public class RemoveHallEndpoint implements Serializable, RemoveHallEndpointLocal {

    @Override
    public HallDTO getHallByName(String name) {
        return null;
    }

    @Override
    @RolesAllowed("removeHall")
    public void removeHall(String name) {

    }
}

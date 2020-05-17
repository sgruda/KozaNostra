package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.ListHallsEndpointLocal;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@TransactionAttribute(TransactionAttributeType.NEVER)
@Stateful
public class ListHallsEndpoint implements Serializable, ListHallsEndpointLocal {

    @Override
    public List<HallDTO> getFilteredHalls(String hallFilter) throws AppBaseException {
        // TODO implementacja
        return new ArrayList<>();
    }
}

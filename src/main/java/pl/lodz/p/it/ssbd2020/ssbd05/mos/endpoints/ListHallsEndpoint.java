package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.ListHallsEndpointLocal;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@TransactionAttribute(TransactionAttributeType.NEVER)
@Stateful
@Local
public class ListHallsEndpoint implements Serializable, ListHallsEndpointLocal {

    @Override
    public List<HallDTO> getFilteredHalls(String hallFilter) {
        // TODO implementacja
        return new ArrayList<>();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.ListHallsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.managers.HallManager;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ListHallsEndpoint implements Serializable, ListHallsEndpointLocal {
    @Inject
    private HallManager hallManager;

    @Override
    @PermitAll
    public List<HallDTO> getAllHalls() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public List<HallDTO> getFilteredHalls(String hallFilter) throws AppBaseException {
        // TODO implementacja
        return new ArrayList<>();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.HallDetailsEndpointLocal;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class HallDetailsEndpoint implements Serializable, HallDetailsEndpointLocal {

    @Override
    @PermitAll
    public HallDTO getHallByName(String name) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

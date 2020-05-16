package pl.lodz.p.it.ssbd2020.ssbd05.mos.managers;


import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.HallFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.TrackerInterceptor;

import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class HallManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private HallFacade hallFacade;
}

package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ExtraServiceFacade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ExtraServiceManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ExtraServiceFacade extraServiceFacade;


    @RolesAllowed("getAllExtraServices")
    public List<ExtraService> getAllExtraServices() throws AppBaseException {
        // TODO implementacja
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("addExtraService")
    public void addExtraService(ExtraService extraService) throws AppBaseException {
        extraServiceFacade.create(extraService);
    }

    @RolesAllowed("getExtraServiceByName")
    public ExtraService getExtraServiceByName(String name) throws AppBaseException {
        // TODO implementacja
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("changeExtraServiceActivity")
    void changeActivity(ExtraService extraService) throws AppBaseException {
        // TODO implementacja
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("editExtraService")
    public void editExtraService(ExtraService extraService) throws AppBaseException{
        //TODO Implemnetacja
        throw new UnsupportedOperationException();
    }
}

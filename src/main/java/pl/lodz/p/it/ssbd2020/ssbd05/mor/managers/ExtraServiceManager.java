package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ExtraServiceFacade;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
public class ExtraServiceManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ExtraServiceFacade extraServiceFacade;

    public List<ExtraService> getAllExtraServices() throws AppBaseException {
        // TODO implementacja
        return new ArrayList<>();
    }

    public void addExtraService(ExtraService extraService) throws AppBaseException {
        // TODO implementacja
    }

    public ExtraService getExtraServiceByName(String name) throws AppBaseException {
        // TODO implementacja
        return null;
    }

    void changeActivity(ExtraService extraService) throws AppBaseException {
        // TODO implementacja
    }
}

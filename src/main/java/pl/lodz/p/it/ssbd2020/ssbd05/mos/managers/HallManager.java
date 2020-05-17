package pl.lodz.p.it.ssbd2020.ssbd05.mos.managers;


import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.HallFacade;

import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
public class HallManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private HallFacade hallFacade;

    public Collection<Hall> getFilteredHalls(String hallFilter) throws AppBaseException {
        // TODO implement
        return new ArrayList<>();
    }
}

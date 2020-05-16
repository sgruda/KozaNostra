package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;


import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ReservationFacade;

import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
public class ReservationManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ReservationFacade reservationFacade;
}

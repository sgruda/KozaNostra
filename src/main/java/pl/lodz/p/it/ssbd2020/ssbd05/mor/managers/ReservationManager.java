package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;


import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@LocalBean
public class ReservationManager {
}

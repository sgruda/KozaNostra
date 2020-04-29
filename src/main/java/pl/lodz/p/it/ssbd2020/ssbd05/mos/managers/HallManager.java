package pl.lodz.p.it.ssbd2020.ssbd05.mos.managers;


import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class HallManager {
}

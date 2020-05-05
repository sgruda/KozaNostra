package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import javax.ejb.*;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ReservationEndpoint implements Serializable {
}

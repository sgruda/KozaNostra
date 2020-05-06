package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.io.Serializable;

@Stateful
@Named
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class HallEndpoint implements Serializable {
}

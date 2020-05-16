package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.utils.TrackerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Stateful
@Named
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class HallEndpoint implements Serializable {
}

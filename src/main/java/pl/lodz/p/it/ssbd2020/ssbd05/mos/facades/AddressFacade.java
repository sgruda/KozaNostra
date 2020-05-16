package pl.lodz.p.it.ssbd2020.ssbd05.mos.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "ssbd05mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }

    @Override
    //    @RolesAllowed()
    public void create(Address entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<Address> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<Address> findAll() {
        return super.findAll();
    }
}

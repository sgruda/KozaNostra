package pl.lodz.p.it.ssbd2020.ssbd05.mos.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.TrackerInterceptor;

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
public class HallFacade extends AbstractFacade<Hall> {

    @PersistenceContext(unitName = "ssbd05mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HallFacade() {
        super(Hall.class);
    }

    @Override
    //    @RolesAllowed()
    public void create(Hall entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    //    @RolesAllowed()
    public void edit(Hall entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    //    @RolesAllowed()
    public void remove(Hall entity) {
        super.remove(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<Hall> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<Hall> findAll() {
        return super.findAll();
    }
}

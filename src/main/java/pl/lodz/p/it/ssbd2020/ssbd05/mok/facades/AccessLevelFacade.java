package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;

import javax.annotation.security.PermitAll;
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
public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

    @PersistenceContext(unitName = "ssbd05mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccessLevelFacade() {
        super(AccessLevel.class);
    }

    @Override
    @PermitAll
    public void create(AccessLevel entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @PermitAll
    public void edit(AccessLevel entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<AccessLevel> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<AccessLevel> findAll() {
        return super.findAll();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
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
public class ExtraServiceFacade extends AbstractFacade<ExtraService> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExtraServiceFacade() {
        super(ExtraService.class);
    }

    @Override
    //    @RolesAllowed()
    public void create(ExtraService entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    //    @RolesAllowed()
    public void edit(ExtraService entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<ExtraService> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<ExtraService> findAll() {
        return super.findAll();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ExtraServiceAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * Fasada dla encji ExtraService
 */
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

    /**
     * Konstruktur bezprarametrowy fasady
     */
    public ExtraServiceFacade() {
        super(ExtraService.class);
    }

    @Override
    @RolesAllowed("addExtraService")
    public void create(ExtraService entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException | PersistenceException e) {
            if(e.getMessage().contains("extra_service_service_name_uindex"))
                throw new ExtraServiceAlreadyExistsException("error.extraservice.exists");
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @RolesAllowed({"editExtraService", "changeExtraServiceActivity"})
    public void edit(ExtraService entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public Optional<ExtraService> find(Object id) {
        return super.find(id);
    }

    @Override
    @RolesAllowed({"getAllExtraServices", "changeExtraServiceActivity"})
    public List<ExtraService> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Pobierz ExtraService według nazwy
     *
     * @param name nazwa usługi dodatkowej do pobrania
     * @return optional ExtraService
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getExtraServiceByName")
    public Optional<ExtraService> findByName(String name) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("ExtraService.findByServiceName", ExtraService.class)
            .setParameter("serviceName", name).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public void remove(ExtraService entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

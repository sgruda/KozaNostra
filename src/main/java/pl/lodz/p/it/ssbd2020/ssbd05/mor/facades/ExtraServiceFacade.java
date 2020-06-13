package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
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
import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasady dla typu ExtraService
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
     * Konstruktor bezparametrowy klasy ExtraServiceFacade
     */
    public ExtraServiceFacade() {
        super(ExtraService.class);
    }

    /**
     * Metoda odpowiedzialna za utrwalenie obiekty typu ExtraService w bazie
     *
     * @param entity obiekt encyjny typu ExtraService
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
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

    /**
     * Edytuj usługę dodatkową
     *
     * @param entity encja
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed({"editExtraService", "changeExtraServiceActivity"})
    public void edit(ExtraService entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (DatabaseException ex) {
            if (ex.getCause() instanceof SQLNonTransientConnectionException) {
                throw new DatabaseConnectionException(ex);
            } else {
                throw new DatabaseQueryException(ex);
            }
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (PersistenceException e) {
            throw new DatabaseQueryException(e);
        }
    }

    /**
     * Metoda odpowiedzialna za pobranie z bazy usługi o podanym id
     *
     * @param id id usługi dodatkowej
     * @return obiekt typu Optional<ExtraService>
     */
    @Override
    @DenyAll
    public Optional<ExtraService> find(Object id) {
        return super.find(id);
    }

    /**
     * Pobierz listę wszystkich usług dodatkowych
     *
     * @return lista wszystkich usług dodatkowych
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
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
     * Pobierz usługę dodatkową na podstawie nazwy
     *
     * @param name nazwa usługi dodatkowej
     * @return obiekt typu Optional
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

    /**
     * Metoda odpowiedzialna za usuwanie usługi z bazy
     *
     * @param entity obiekt typu ExtraService
     */
    @Override
    @DenyAll
    public void remove(ExtraService entity) throws AppBaseException {
        super.remove(entity);
    }

    /**
     * Metoda odpowiedzialna za zliczenie obiektów typu ExtraService w bazie
     */
    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

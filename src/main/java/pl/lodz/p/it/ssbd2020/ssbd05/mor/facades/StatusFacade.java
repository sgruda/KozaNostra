package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 * Fasada statusów - typ Status
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class StatusFacade extends AbstractFacade<Status> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public StatusFacade() {
        super(Status.class);
    }

    @Override
    @DenyAll
    public Optional<Status> find(Object id) {
        return super.find(id);
    }

    @Override
    @PermitAll
    public List<Status> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Metoda odpowiedzialna za pobieranie statusu z bazy danych na podstawie jego nazwy.
     *
     * @param statusName Nazwa statusu
     * @return Optional Status
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed({"getStatusByName", "getStatusCanceled"})
    public Optional<Status> findByStatusName(String statusName) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("Status.findByStatusName", Status.class)
                    .setParameter("statusName", statusName).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public void create(Status entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void edit(Status entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    @DenyAll
    public void remove(Status entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

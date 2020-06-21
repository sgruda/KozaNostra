package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;

import javax.annotation.security.DenyAll;
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
 * Fasada dla encji Hall
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class HallFacade extends AbstractFacade<Hall> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Konstruktur bezprarametrowy fasady
     */
    public HallFacade() {
        super(Hall.class);
    }

    @Override
    @DenyAll
    public Optional<Hall> find(Object id) {
        return super.find(id);
    }

    @Override
    @DenyAll
    public List<Hall> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public void create(Hall entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void edit(Hall entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    @DenyAll
    public void remove(Hall entity) throws AppBaseException {
        super.remove(entity);
    }
    /**
     * Pobierz Hall według nazwy
     *
     * @param name nazwa sali do pobrania
     * @return optional Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getHallByName")
    public Optional<Hall> findByName(String name) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("Hall.findByName", Hall.class)
                    .setParameter("name", name).getSingleResult());
        } catch (NoResultException noResultException) {
            return Optional.empty();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }

}

package pl.lodz.p.it.ssbd2020.ssbd05.mos.facades;

import lombok.extern.java.Log;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.ws.rs.NotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasady dla typu Hall
 */
@Log
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
    @RolesAllowed("addHall")
    public void create(Hall entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException | PersistenceException e) {
            if (e.getMessage().contains("hall_name_uindex")) {
                throw new HallAlreadyExistsException(e);
            } else {
                throw new DatabaseConnectionException(e);
            }
        }
    }

    @PermitAll
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
    @RolesAllowed({"editHall", "changeHallActivity"})
    public void edit(Hall entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @RolesAllowed("removeHall")
    public void remove(Hall entity) throws AppBaseException {
        try {
            this.em.createNamedQuery("RemoveHall",Hall.class).setParameter("name",entity.getName()).executeUpdate();
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public Optional<Hall> find(Object id) {
        return super.find(id);
    }

    @Override
    @PermitAll
    public List<Hall> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Metoda odpowiedzialna za filtrowanie listy sal zgodnie z podanym ciągiem znaków
     *
     * @param hallFilter Ciąg znaków do filtrowania
     * @return Kolekcja obiektów typu Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @PermitAll
    public Collection<Hall> filter(String hallFilter) throws AppBaseException {
        try {
            return em.createNamedQuery("Hall.filterByNameAndAddress", Hall.class)
                    .setParameter("filter", hallFilter).getResultList();
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

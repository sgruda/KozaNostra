package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import lombok.extern.java.Log;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.AverageGuestNumber;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
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
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasady dla typu AverageGuestNumberFacade
 */
@Log
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class AverageGuestNumberFacade extends AbstractFacade<AverageGuestNumber> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Konstruktor bezparametrowy klasy AverageGuestNumberFacade
     */
    public AverageGuestNumberFacade() {
        super(AverageGuestNumber.class);
    }

    /**
     * Edytuj agregat
     *
     * @param entity obiekty encyjny typu AverageGuestNumber
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed("changeReservationStatus")
    public void edit(AverageGuestNumber entity) throws AppBaseException {
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
    public Optional<AverageGuestNumber> find(Object id) {
        return super.find(id);
    }

    @Override
    @PermitAll
    public List<AverageGuestNumber> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (NoResultException e) {
            log.severe("An error occurred while loading an aggregate");
            throw new AppBaseException(e.getMessage());
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public void create(AverageGuestNumber entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void remove(AverageGuestNumber entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

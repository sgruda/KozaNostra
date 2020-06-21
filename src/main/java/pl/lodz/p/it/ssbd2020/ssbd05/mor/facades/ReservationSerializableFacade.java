package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import lombok.extern.java.Log;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.DateOverlapException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import java.util.Optional;

/**
 * Fasada rezerwacji - dla encji Reservation
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Log
@Interceptors(TrackerInterceptor.class)
public class ReservationSerializableFacade extends AbstractFacade<Reservation> {

    @PersistenceContext(unitName = "ssbd05morPUSerializable")
    private EntityManager em;

    /**
     * Konstruktor bezparametrowy
     */
    public ReservationSerializableFacade() {
        super(Reservation.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @RolesAllowed("createReservation")
    public void create(Reservation entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException ex) {
            if (ex.getCause() instanceof SQLNonTransientConnectionException) {
                throw new DatabaseConnectionException(ex);
            } else {
                throw new DatabaseQueryException(ex);
            }
        } catch (PersistenceException e) {
            if (e.getMessage().contains("reservation_overlap_dates_ck")) {
                throw new DateOverlapException();
            } else {
                throw new DatabaseQueryException(e);
            }
        }
    }

    @Override
    @RolesAllowed({"changeReservationStatus", "cancelReservation", "editReservation"})
    public void edit(Reservation entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (DatabaseException ex) {
            if (ex.getCause() instanceof SQLNonTransientConnectionException) {
                throw new DatabaseConnectionException(ex);
            } else {
                throw new DatabaseQueryException(ex);
            }
        } catch (PersistenceException e) {
            if (e.getMessage().contains("reservation_overlap_dates_ck")) {
                throw new DateOverlapException();
            } else {
                throw new DatabaseQueryException(e);
            }
        }
    }

    @Override
    @DenyAll
    public Optional<Reservation> find(Object id) {
        return super.find(id);
    }

    @Override
    @DenyAll
    public List<Reservation> findAll() throws AppBaseException {
            return super.findAll();
    }

    @Override
    @DenyAll
    public void remove(Reservation entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

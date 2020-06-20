package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import lombok.extern.java.Log;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.EmailAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.LoginAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.DateOverlapException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReservationAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReservationNotFoundException;
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
import java.time.LocalDateTime;
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
public class ReservationFacade extends AbstractFacade<Reservation> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    /**
     * Konstruktor bezparametrowy
     */
    public ReservationFacade() {
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

    /**
     * Metoda odpowiedzialna za edycję obiektu encji reprezentującej rezerwację w bazie danych
     *
     * @param entity Obiekt typu Reservation
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed({"changeReservationStatus", "cancelReservation", "editReservation"})
    public void edit(Reservation entity) throws AppBaseException {
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
    public Optional<Reservation> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda odpowiedzialna za pobieranie wszystkich rezerwacji z bazy danych
     *
     * @return Lista obiektów typu Reservation
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed({"getAllReservations", "getAllEventTypes"})
    public List<Reservation> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Metoda odpowiedzialna za pobieranie z bazy danych rezerwacji na podstawie jej numeru.
     *
     * @param number numer rezerwacji
     * @return optional Reservation
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getReservationByNumber")
    public Optional<Reservation> findByNumber(String number) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("Reservation.findByReservationNumber", Reservation.class)
                    .setParameter("reservationNumber", number).getSingleResult());
        } catch (NoResultException noResultException) {
            throw new ReservationNotFoundException(noResultException);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Metoda odpowiadająca za filtrowanie rezerwacji
     *
     * @param filter filtr, rezerwacje filtorwane są po loginie, imieniu, nazwisku oraz numerze rezerwacji. Wielkość liter nie ma znaczenia.
     * @return przefiltrowana lista rezerwacji
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("filterReservations")
    public List<Reservation> filterReservations(String filter) throws AppBaseException {
        try {
            return em.createNamedQuery("Reservation.filterByLoginAndNames", Reservation.class)
                    .setParameter("filter", filter).getResultList();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Pobierz rezerwacje według nazwy użytkownika
     *
     * @param login nazwa użytkownika
     * @return lista  rezerwacji danego użytkownika
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed({"getAllUsersReservations", "getUserReviewableReservations"})
    public List<Reservation> findByLogin(String login) throws AppBaseException {
        try {
            Account account = this.em.createNamedQuery("Account.findByLogin", Account.class)
                    .setParameter("login", login).getSingleResult();
            return this.em.createNamedQuery("Reservation.findByClientId", Reservation.class).setParameter("id", account.getId()).getResultList();
        } catch (NoResultException noResultException) {
            throw new ReservationNotFoundException(noResultException);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
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

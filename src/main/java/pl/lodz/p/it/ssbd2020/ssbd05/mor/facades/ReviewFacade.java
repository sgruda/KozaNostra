package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReviewNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;

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
 * Fasada opinii - dla encji Review
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ReviewFacade extends AbstractFacade<Review> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public ReviewFacade() {
        super(Review.class);
    }

    @Override
    @RolesAllowed("addReview")
    public void create(Review entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }
    /**
     * Metoda odpowiedzialna za edycję obiektu encji reprezentującej opinię w bazie danych
     *
     * @param entity Obiekt typu Review
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed("editReview")
    public void edit(Review entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Metoda odpowiedzialna za usunięcie obiektu encji reprezentującej opinię w bazie danych
     *
     * @param entity Obiekt typu Review
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @Override
    @RolesAllowed("removeReview")
    public void remove(Review entity) throws AppBaseException {
        try {
            super.remove(entity);
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException(e);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public Optional<Review> find(Object id) {
        return super.find(id);
    }
    /**
     * Metoda odpowiedzialna za pobieranie wszystkich opinii z bazy danych
     *
     * @return Lista obiektów typu Review
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @Override
    @PermitAll
    public List<Review> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Find by login list.
     *
     * @param login the login
     * @return the list
     * @throws AppBaseException the app base exception
     */
    @RolesAllowed("getUserReviewableReservations")
    public List<Review> findByLogin(String login) throws AppBaseException {
        try {
            Account account = this.em.createNamedQuery("Account.findByLogin", Account.class)
                    .setParameter("login", login).getSingleResult();
            return this.em.createNamedQuery("Review.findByClientId", Review.class).setParameter("id", account.getId()).getResultList();
        }catch (NoResultException noResultException) {
            throw new ReviewNotFoundException(noResultException);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    /**
     * Metoda odpowiedzialna za pobieranie z bazy danych opinii na podstawie jej numeru.
     *
     * @param reviewNumber numer opinii
     * @return optional Review
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed({"getReviewByReviewNumber", "editReview"})
    public Optional<Review> findByNumber(String reviewNumber) throws AppBaseException {
        try{
            return Optional.ofNullable(this.em.createNamedQuery("Review.findByReviewNumber", Review.class)
                    .setParameter("reviewNumber", reviewNumber).getSingleResult());
        } catch (NoResultException noResultException) {
            throw new ReviewNotFoundException(noResultException);
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

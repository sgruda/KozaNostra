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
     * Metoda zwracająca listę opinii wystawionych przez konto o podanej nazwie użytkownika.
     *
     * @param login nazwa użytkownika
     * @return lista opinii
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
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

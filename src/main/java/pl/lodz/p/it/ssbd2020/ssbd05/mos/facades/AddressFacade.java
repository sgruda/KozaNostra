package pl.lodz.p.it.ssbd2020.ssbd05.mos.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;
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
 * Klasa fasady dla encji Address
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "ssbd05mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Konstruktor bezparametrowy dla klasy AddressFacade
     */
    public AddressFacade() {
        super(Address.class);
    }

    @Override
    @RolesAllowed("addHall")
    public void create(Address entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public Optional<Address> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda odpowiedzialna za pobieranie encji reprezentującej adres o danej
     * nazwie ulicy, numerze ulicy i nazwie miasta z bazy danych
     *
     * @param street Nazwa ulicy
     * @param number Numer ulicy
     * @param city   Nazwa miasta
     * @return Obiekt typu Address opakowany w obiekt Optional
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("addHall")
    public Optional<Address> findByStreetAndNumberAndCity(String street, int number, String city) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("Address.findByStreetAndNumberAndCity", Address.class)
                    .setParameter("street", street)
                    .setParameter("number", number)
                    .setParameter("city", city)
                    .getSingleResult());
        } catch (NoResultException noResultException) {
            return Optional.empty();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @RolesAllowed("getAllAddresses")
    public List<Address> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public void edit(Address entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    @DenyAll
    public void remove(Address entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

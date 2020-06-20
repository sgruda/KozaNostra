package pl.lodz.p.it.ssbd2020.ssbd05.mos.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasady dla encji EventType
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless(name = "EventTypesFacadeMOS")
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class EventTypesFacade extends AbstractFacade<EventType> {

    @PersistenceContext(unitName = "ssbd05mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Konstruktor bezparametrowy dla klasy EventTypesFacade
     */
    public EventTypesFacade() {
        super(EventType.class);
    }

    @Override
    @DenyAll
    public Optional<EventType> find(Object id) {
        return super.find(id);
    }

    @Override
    @RolesAllowed("getAllEventTypes")
    public List<EventType> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public void create(EventType entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void edit(EventType entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    @DenyAll
    public void remove(EventType entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

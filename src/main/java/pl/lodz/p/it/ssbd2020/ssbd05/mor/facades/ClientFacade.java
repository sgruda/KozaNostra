package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;

import javax.annotation.security.DenyAll;
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

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless(name = "ClientFacadeMOR")
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }

    @Override
    @DenyAll
    public Optional<Client> find(Object id) {
        return super.find(id);
    }

    @Override
    @DenyAll
    public List<Client> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    @DenyAll
    public void create(Client entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void edit(Client entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    @DenyAll
    public void remove(Client entity) throws AppBaseException {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public int count() {
        return super.count();
    }
}

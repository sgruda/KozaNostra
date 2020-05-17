package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.AverageGuestNumber;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;

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

    public AverageGuestNumberFacade() {
        super(AverageGuestNumber.class);
    }

    @Override
    //    @RolesAllowed()
    public void edit(AverageGuestNumber entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }

    @Override
    //    @RolesAllowed()
    public Optional<AverageGuestNumber> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<AverageGuestNumber> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }
}

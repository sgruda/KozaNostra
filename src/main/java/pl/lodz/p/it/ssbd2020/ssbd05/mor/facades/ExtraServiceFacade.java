package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ExtraServiceAlreadyExistsException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
public class ExtraServiceFacade extends AbstractFacade<ExtraService> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExtraServiceFacade() {
        super(ExtraService.class);
    }

    @Override
    //    @RolesAllowed()
    public void create(ExtraService entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException | PersistenceException e) {
            if(e.getMessage().contains("extra_service_service_name_uindex"))
                throw new ExtraServiceAlreadyExistsException();
            throw new DatabaseConnectionException();
        }
    }

    @Override
    //    @RolesAllowed()
    public void edit(ExtraService entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }

    @Override
    //    @RolesAllowed()
    public Optional<ExtraService> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<ExtraService> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }
}

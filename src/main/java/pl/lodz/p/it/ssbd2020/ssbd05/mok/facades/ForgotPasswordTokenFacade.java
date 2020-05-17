package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.ForgotPasswordToken;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;

import javax.annotation.security.PermitAll;
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

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ForgotPasswordTokenFacade extends AbstractFacade<ForgotPasswordToken> {

    @PersistenceContext(unitName = "ssbd05mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ForgotPasswordTokenFacade() {
        super(ForgotPasswordToken.class);
    }

    @Override
    @PermitAll
    public void create(ForgotPasswordToken entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }

    @Override
    //    @RolesAllowed()
    public void edit(ForgotPasswordToken entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }

    @Override
    //    @RolesAllowed()
    public void remove(ForgotPasswordToken entity) throws AppBaseException {
        try {
            super.remove(entity);
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }

    @Override
    //    @RolesAllowed()
    public Optional<ForgotPasswordToken> find(Object id) {
        return super.find(id);
    }

    @Override
    public List<ForgotPasswordToken> findAll() throws AppBaseException {
        try {
            return super.findAll();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }

    @PermitAll
    public Optional<ForgotPasswordToken> findByHash(String hash) throws AppBaseException {
        try {
            return Optional.ofNullable(this.em.createNamedQuery("ForgotPasswordToken.findByHash", ForgotPasswordToken.class)
                    .setParameter("hash", hash).getSingleResult());
        } catch (NoResultException e) {
            throw new AppBaseException();
        } catch (DatabaseException | PersistenceException e) {
            throw new DatabaseConnectionException();
        }
    }
}

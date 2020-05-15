package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.ForgotPasswordToken;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
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
    //    @RolesAllowed()
    public void create(ForgotPasswordToken entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    //    @RolesAllowed()
    public void edit(ForgotPasswordToken entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    //    @RolesAllowed()
    public void remove(ForgotPasswordToken entity) {
        super.remove(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<ForgotPasswordToken> find(Object id) {
        return super.find(id);
    }

    @Override
    public List<ForgotPasswordToken> findAll() {
        return super.findAll();
    }
}

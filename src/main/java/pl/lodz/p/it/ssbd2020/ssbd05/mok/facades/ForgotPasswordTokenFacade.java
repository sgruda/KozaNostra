package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.ForgotPasswordToken;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
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
}

package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.AverageGuestNumber;
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
        super.edit(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<AverageGuestNumber> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<AverageGuestNumber> findAll() {
        return super.findAll();
    }
}

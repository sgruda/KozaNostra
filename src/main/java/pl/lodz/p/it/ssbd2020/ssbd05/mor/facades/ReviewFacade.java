package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.TrackerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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

    public ReviewFacade() {
        super(Review.class);
    }

    @Override
    //    @RolesAllowed()
    public void create(Review entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    //    @RolesAllowed()
    public void edit(Review entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    //    @RolesAllowed()
    public void remove(Review entity) {
        super.remove(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<Review> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<Review> findAll() {
        return super.findAll();
    }
}

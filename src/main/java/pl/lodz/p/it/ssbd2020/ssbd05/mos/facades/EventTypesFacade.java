package pl.lodz.p.it.ssbd2020.ssbd05.mos.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventTypes;


@Stateless
public class EventTypesFacade extends AbstractFacade<EventTypes> {

    @PersistenceContext(unitName = "ssbd05mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventTypesFacade() {
        super(EventTypes.class);
    }
    
}

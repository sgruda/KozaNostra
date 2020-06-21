package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.AverageGuestNumberMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.AverageGuestNumberDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.AverageGuestNumber;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.GetAggregateEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReservationManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 * Punkt dostępowy implementujący interfejs GetAggregateEndpointLocal, który pośredniczy
 * przy pobieraniu z bazy danych obiektu encyjnego agregatu
 */
@Log
@Stateful
@Interceptors(TrackerInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class GetAggregateEndpoint implements GetAggregateEndpointLocal {

    @Inject
    private ReservationManager reservationManager;
    private AverageGuestNumber aggregate;

    @Override
    public AverageGuestNumberDTO getAggregate() throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                aggregate = reservationManager.getAggregate();
                rollback = reservationManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + reservationManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return AverageGuestNumberMapper.INSTANCE.toAverageGuestNumberDTO(aggregate);
    }
}

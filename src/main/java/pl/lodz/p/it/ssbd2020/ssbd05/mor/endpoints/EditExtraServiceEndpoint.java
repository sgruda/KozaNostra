package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ExtraServiceMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditExtraServiceEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ExtraServiceManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class EditExtraServiceEndpoint implements Serializable, EditExtraServiceEndpointLocal {
    @Inject
    private ExtraServiceManager extraServiceManager;
    private ExtraService extraService;

    @Override
    @RolesAllowed("getExtraServiceByName")
    public ExtraServiceDTO getExtraServiceByName(String name) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                extraService = extraServiceManager.getExtraServiceByName(name);
                rollback = extraServiceManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + extraServiceManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return ExtraServiceMapper.INSTANCE.toExtraServiceDTO(extraService);
    }

    @Override
    @RolesAllowed("changeExtraServiceActivity")
    public void changeActivity(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                extraServiceManager.changeActivity(extraService);
                rollback = extraServiceManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + extraServiceManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter <= ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @Override
    @RolesAllowed("editExtraService")
    public void editExtraService(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        //TODO Implementacja
        throw new UnsupportedOperationException();
    }
}

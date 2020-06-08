package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ExtraServiceMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListExtraServicesEndpointLocal;
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
import java.util.List;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ListExtraServicesEndpoint implements Serializable, ListExtraServicesEndpointLocal {

    @Inject
    private ExtraServiceManager extraServiceManager;
    private List<ExtraService> list;

    @Override
    @RolesAllowed("getAllExtraServices")
    public List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                list = extraServiceManager.getAllExtraServices();
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
        return ExtraServiceMapper.INSTANCE.toExtraServiceDTOList(list);
    }

    private ExtraService findFromList(String name) {
        return list.stream().filter(extraService -> extraService.getServiceName().equals(name)).findFirst().get();
    }

    @Override
    @RolesAllowed("changeExtraServiceActivity")
    public void changeActivity(ExtraServiceDTO extraServiceDTO) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                extraServiceManager.changeActivity(findFromList(extraServiceDTO.getServiceName()));
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
}

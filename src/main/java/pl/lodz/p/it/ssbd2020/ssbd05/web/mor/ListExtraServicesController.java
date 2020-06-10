package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListExtraServicesEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Log
@Named
@ViewScoped
public class ListExtraServicesController implements Serializable {

    @Inject
    ListExtraServicesEndpointLocal listExtraServicesEndpoint;

    @Getter
    private List<ExtraServiceDTO> extraServices;

    @PostConstruct
    public void init() {
        try {
            extraServices = listExtraServicesEndpoint.getAllExtraServices();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public void changeActivity(ExtraServiceDTO extraServiceDTO) {
        try {
            listExtraServicesEndpoint.changeActivity(extraServiceDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.listextraservices.activity.changed");
            reload();
        } catch (AppOptimisticLockException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.extraservice.optimistic.lock");
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    private void reload() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(((HttpServletRequest) externalContext.getRequest()).getRequestURI());
        } catch (IOException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }


    public String goToEditPage(String extraServiceName){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedExtraService", extraServiceName);
        return "editExtraServicePage";
    }
}

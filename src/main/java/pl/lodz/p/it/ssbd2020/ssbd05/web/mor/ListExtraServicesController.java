package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditExtraServiceEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListExtraServicesEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Log
@Named
@ViewScoped
public class ListExtraServicesController implements Serializable {

    @Inject
    ListExtraServicesEndpointLocal listExtraServicesEndpoint;

    @Inject
    EditExtraServiceEndpointLocal editExtraServiceEndpoint;

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
}

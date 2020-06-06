package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditExtraServiceEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Named
@RequestScoped
public class ActivationExtraServiceController implements Serializable {

    @Inject
    private EditExtraServiceEndpointLocal editExtraServiceEndpoint;

    public void changeActivity(ExtraServiceDTO extraServiceDTO) {
        try {
            editExtraServiceEndpoint.changeActivity(extraServiceDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.listextraservices.activity.changed");
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
}

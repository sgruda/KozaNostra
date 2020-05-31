package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.AddExtraServiceEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

@Log
@Getter
@Setter
@Named
@RequestScoped
public class AddExtraServiceController {

    @Inject
    private AddExtraServiceEndpointLocal addExtraServiceEndpoint;

    private String name;
    private double price;
    private String description;
    private boolean active = false;

    public String goBack() {
        return "home";
    }

    public void addExtraService() {
        ExtraServiceDTO extraServiceDTO = new ExtraServiceDTO();
        extraServiceDTO.setServiceName(name);
        extraServiceDTO.setDescription(description);
        extraServiceDTO.setPrice(price);
        extraServiceDTO.setActive(active);

        try {
            addExtraServiceEndpoint.addExtraService(extraServiceDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.addextraservice.success");
        } catch (ValidationException e) {
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }
}

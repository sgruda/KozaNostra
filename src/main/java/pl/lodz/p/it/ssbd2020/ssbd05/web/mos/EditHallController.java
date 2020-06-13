package pl.lodz.p.it.ssbd2020.ssbd05.web.mos;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.EditHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Log
@Getter
@Setter
@Named
@ViewScoped
public class EditHallController implements Serializable {

    @Inject
    private EditHallEndpointLocal editHallEndpoint;

    private HallDTO hall;
    private List<String> eventTypes;

    public String onLoad() {
        String selectedHallName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedHallName");
        try {
            hall = editHallEndpoint.getHallByName(selectedHallName);
            eventTypes = editHallEndpoint.getAllEventTypes();
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            return goBack();
        }
        return "";
    }

    public void editHall() {
        log.info("CONTROLLER: " + Arrays.asList(eventTypes.toArray()));
        try {
            editHallEndpoint.editHall(hall);
            ResourceBundles.emitMessageWithFlash(null, "page.edithall.success");
        } catch (AppOptimisticLockException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.hall.optimisticlock");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (ValidationException e) {
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }

    public String translateEventType(String eventTypeName) {
        return ResourceBundles.getTranslatedText(eventTypeName);
    }

    public String goBack() {
        return "hallDetails";
    }
}

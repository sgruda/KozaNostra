package pl.lodz.p.it.ssbd2020.ssbd05.web.mos;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallHasReservationsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.HallDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.RemoveHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@ViewScoped
public class HallDetailsController implements Serializable {

    @Inject
    private HallDetailsEndpointLocal hallDetailsEndpoint;

    @Inject
    private RemoveHallEndpointLocal removeHallEndpoint;

    @Getter
    private HallDTO hall;

    public String onLoad() {
        String selectedHallName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedHallName");
        try {
            this.hall = hallDetailsEndpoint.getHallByName(selectedHallName);
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            return goBack();
        }
        return "";
    }

    public String listEventTypes() {
        if (hall != null) {
            StringBuilder result = new StringBuilder();
            String[] eventTypes = hall.getEvent_type().toArray(new String[0]);
            for (int i = 0; i < hall.getEvent_type().size(); i++) {
                if (i != hall.getEvent_type().size() - 1) {
                    result.append(ResourceBundles.getTranslatedText(eventTypes[i])).append(", ");
                } else {
                    result.append(ResourceBundles.getTranslatedText(eventTypes[i]));
                }
            }
            return result.toString();
        } else {
            return "";
        }
    }

    public void removeHall(){
        try {
            if(!hall.isActive()) {
                if(hall.getReservationCollection().isEmpty()) {
                    removeHallEndpoint.removeHall(hall);
                    ResourceBundles.emitMessageWithFlash(null, "page.hall.details.delete.success");
                }else throw new HallHasReservationsException();
            }else{
                ResourceBundles.emitErrorMessageWithFlash(null, "page.hall.details.active");
            }
        }catch (HallHasReservationsException ex){
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.severe( ex.getMessage() + ", " +LocalDateTime.now());
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, appBaseException.getMessage());
        }
    }

    public String goToEditForm() {
        return "editHall";
    }

    public String goToReservationPage() {
        return "toReservationPage";
    }

    public String goBack() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedHallName");
        return "goBack";
    }

    //przy przechodzeniu na szczegóły sali chcę zrobić obsługę błędu w przypadku gdy ktoś w międzyczasie ją usunie
}

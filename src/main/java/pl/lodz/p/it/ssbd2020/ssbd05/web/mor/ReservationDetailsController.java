package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.ReservationDetailsEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ReservationDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@ViewScoped
public class ReservationDetailsController implements Serializable {

    @Inject
    private ReservationDetailsEndpointLocal reservationDetailsEndpointLocal;
    @Getter
    private ReservationDTO reservationDTO;
    @Getter
    private ResourceBundles resourceBundles;
    @Getter
    @Setter
    private String extraServices;

    @PostConstruct
    public void init(){
        try {
            resourceBundles = new ResourceBundles();
            this.reservationDTO = reservationDetailsEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            for(String extraService: reservationDTO.getExtraServiceCollection()){
                extraServices += extraService;
                extraServices += " ";
            }
            extraServices = extraServices.replace("null", "");
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }

    public String goBack(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedReservationNumber");
        return "back";
    }

}

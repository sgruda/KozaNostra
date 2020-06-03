package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Properties;

@ViewScoped
@Named
@Log
public class ReservationUtils implements Serializable {



    private Properties eventTypeProperties;
    private Properties statusProperties;

    public boolean isEventTypeConference(String eventTypeName){
        return eventTypeName.equalsIgnoreCase((eventTypeProperties.getProperty("eventTypeConference")));
    }

    public boolean isEventTypeWedding(String eventTypeName){
        return eventTypeName.equalsIgnoreCase((eventTypeProperties.getProperty("eventTypeWedding")));
    }

    public boolean isEventTypeParty(String eventTypeName){
        return eventTypeName.equalsIgnoreCase((eventTypeProperties.getProperty("eventTypeParty")));

    }

    public boolean isEventTypeOther(String eventTypeName){
        return eventTypeName.equalsIgnoreCase((eventTypeProperties.getProperty("eventTypeOther")));

    }

    public boolean isStatusPaid(String status){
        return status.equalsIgnoreCase((statusProperties.getProperty("statusPaid")));
    }

    public boolean isStatusSubmitted(String status){
        return status.equalsIgnoreCase((statusProperties.getProperty("statusSubmitted")));
    }

    public boolean isStatusConfirmed(String status){
        return status.equalsIgnoreCase((statusProperties.getProperty("statusConfirmed")));
    }

    public boolean isStatusCancelled(String status){
        return status.equalsIgnoreCase((statusProperties.getProperty("statusCancelled")));
    }

    public boolean isStatusFinished(String status){
        return status.equalsIgnoreCase((statusProperties.getProperty("statusFinished")));
    }


    @PostConstruct
    private void init() {
        this.eventTypeProperties = new Properties();
        this.statusProperties = new Properties();
        try {
            this.eventTypeProperties = ResourceBundles.loadProperties("config.event_type.properties");
            this.statusProperties = ResourceBundles.loadProperties("config.status.properties");
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
}

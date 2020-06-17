package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.AverageGuestNumberDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.GetAggregateEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Log
@Named
@RequestScoped
public class GetAggregateController {

    @Inject
    GetAggregateEndpointLocal aggregateEndpoint;

    private AverageGuestNumberDTO dto;

    @PostConstruct
    public void init(){
        try {
           dto = aggregateEndpoint.getAggregate();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public String getAverageGuestNumber(){
        if(dto != null){
            String s = ResourceBundles.getTranslatedText("guest.number.average");
            String result = MessageFormat.format(s,dto.getAverage());
            return result;
        }
        else return "";
    }
    public String getTotalGuestNumber(){
        if(dto != null) {
            String s = ResourceBundles.getTranslatedText("guest.number.total");
            String result = MessageFormat.format(s, dto.getGuestSum());
            return result;
        }
        else return "";
    }
}

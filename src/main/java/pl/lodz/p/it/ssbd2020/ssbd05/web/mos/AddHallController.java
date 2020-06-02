package pl.lodz.p.it.ssbd2020.ssbd05.web.mos;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.AddHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
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
public class AddHallController implements Serializable {

    @Inject
    private AddHallEndpointLocal addHallEndpoint;

    private HallDTO hall;
    private List<String> eventTypes;
    private List<AddressDTO> addresses;
    private boolean newAddress;
    private AddressDTO address;

    @PostConstruct
    public void init() {
        hall = new HallDTO();
        hall.setActive(false);
        newAddress = true;
        address = new AddressDTO();
        try {
            eventTypes = addHallEndpoint.getAllEventTypes();
            addresses = addHallEndpoint.getAllAddresses();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        log.info(Arrays.toString(eventTypes.toArray()));
        log.info(Arrays.toString(addresses.toArray()));
    }

    public void toggleNewAddress() {
        newAddress = !newAddress;
    }

    public void addHall() {
        hall.setAddress(address);
        log.info("CONTROLLER: " + hall);
        try {
            addHallEndpoint.addHall(hall);
            ResourceBundles.emitMessageWithFlash(null,"page.addhall.created");
        } catch (ValidationException e) {
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
        }
    }

    public String goBack() {
        return "home";
    }
}

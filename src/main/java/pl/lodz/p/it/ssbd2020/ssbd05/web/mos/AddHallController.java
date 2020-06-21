package pl.lodz.p.it.ssbd2020.ssbd05.web.mos;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.AddHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Kontroler odpowiedzialny za dodawanie sali przez użytkownika o poziomie dostępu Menadżer
 */
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

    /**
     * Metoda wywoływana przy wejściu na stronę z formularzem dodawania,
     * wczytuje wszystkie dostępne typy imprez i adresy
     */
    @PostConstruct
    public void init() {
        hall = new HallDTO();
        hall.setActive(false);
        newAddress = false;
        address = new AddressDTO();
        try {
            eventTypes = addHallEndpoint.getAllEventTypes();
            addresses = addHallEndpoint.getAllAddresses();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za dodawanie sali
     */
    public void addHall() {
        hall.setEvent_type(eventTypes);
        hall.setAddress(address);
        try {
            addHallEndpoint.addHall(hall);
            ResourceBundles.emitMessageWithFlash(null, "page.addhall.created");
        } catch (HallAlreadyExistsException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.hall.exists");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (ValidationException e) {
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }

    /**
     * Metoda odpowiedzialna za tłumaczenie nazw typów imprez
     *
     * @param eventTypeName Nazwa typu imprezy
     * @return Zinternacjonalizowana nazwa typu imprezy
     */
    public String translateEventType(String eventTypeName) {
        return ResourceBundles.getTranslatedText(eventTypeName);
    }

    /**
     * Metoda odpowiedzialna za konwersję wartości zmiennej typu boolean na słowo tak/nie
     *
     * @return Zinternacjonalizowany ciąg znaków
     */
    public String translateBoolean() {
        if (newAddress) {
            return ResourceBundles.getTranslatedText("page.common.yes");
        } else {
            return ResourceBundles.getTranslatedText("page.common.no");
        }
    }

    /**
     * Metoda odpowiadająca za przełączanie widoczności formularza, w którym użytkownik dodaje nowy adres
     */
    public void toggleNewAddress() {
        newAddress = !newAddress;
    }

    /**
     * Metoda przenosząca użytkownika na stronę główną
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goBack() {
        return "home";
    }
}

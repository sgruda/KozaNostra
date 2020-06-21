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

/**
 * Kontroler odpowiedzialny za edycję danych sali przez użytkownika o poziomie dostępu Menadżer
 */
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

    /**
     * Metoda wykonywana przy wejściu na stronę z edycją sali, wczytuje dane wybranej sali oraz dostępne typy imprez
     *
     * @return Ciąg znaków, który po pomyślnym wczytaniu danych sali powoduje pozostanie na stronie,
     * natomiast w przeciwnym wypadku powraca do strony z listą wszystkich sal
     */
    public String onLoad() {
        String selectedHallName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedHallName");
        try {
            hall = editHallEndpoint.getHallByName(selectedHallName);
            eventTypes = editHallEndpoint.getAllEventTypes();
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            return "listHalls";
        }
        return "";
    }

    /**
     * Metoda odpowiedzialna za edycję sali
     */
    public void editHall() {
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
     * Metoda przenosząca użytkownika na stronę ze szczegółami sali
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goBack() {
        return "hallDetails";
    }
}

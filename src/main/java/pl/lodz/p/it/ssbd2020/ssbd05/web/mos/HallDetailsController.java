package pl.lodz.p.it.ssbd2020.ssbd05.web.mos;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.HallDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.RemoveHallEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Kontroler odpowiedzialny za wyświetlanie szczegółów wybranej sali
 */
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

    /**
     * Metoda wykonywana przy wejściu na stronę ze szczegółami sali i wczytująca dane wybranej sali
     *
     * @return Ciąg znaków, który po pomyślnym wczytaniu danych sali powoduje pozostanie na stronie, natomiast w przeciwnym wypadku powraca do strony z listą wszystkich sal
     */
    public String onLoad() {
        String selectedHallName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedHallName");
        try {
            this.hall = hallDetailsEndpoint.getHallByName(selectedHallName);
            removeHallEndpoint.getHallByName(selectedHallName);
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            return goBack();
        }
        return "";
    }

    /**
     * Metoda odpowiedzialna za przetłumaczenie i konkatenację nazw typów imprez,
     * do których wybrana sala jest przystosowana
     *
     * @return Zinternacjonalizowane nazwy typów imprez
     */
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

    /**
     * Metoda odpowiedzialna za usunięcie wybranej sali przez użytkownika o dostępie menadżer
     */
    public void removeHall() {
        try {
            removeHallEndpoint.removeHall(hall);
        } catch (AppOptimisticLockException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.hall.optimisticlock.refresh");
        } catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }

    /**
     * Metoda przenosząca użytkownika o poziomie dostępu menadżer na formularz edycji sali
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goToEditForm() {
        return "editHall";
    }

    /**
     * Metoda przenosząca użytkownika o poziomie dostępu klient na formularz rezerwacji sali
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goToReservationPage() {
        return "toReservationPage";
    }

    /**
     * Metoda odpowiedzialna za zmianę aktywności sali na przeciwną w stosunku do obecnego stanu
     */
    public void changeHallActivity(){
        hall.setActive(!hall.isActive());
        try {
            hallDetailsEndpoint.changeActivity(hall);
            if(hall.isActive()){
                ResourceBundles.emitMessageWithFlash(null,"page.halldetails.hall.activated");
            }else ResourceBundles.emitMessageWithFlash(null,"page.halldetails.hall.deactivated");
            refresh();
        }catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessage(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppOptimisticLockException ex){
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null,"error.hall.optimisticlock.refresh");
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za odświeżenie strony
     */
    public void refresh() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch ( IOException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }

    /**
     * Metoda przenosząca użytkownika na stronę z listą wszystkich sal
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goBack() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedHallName");
        return "goBack";
    }
}

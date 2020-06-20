package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

/**
 * Klasa DTO zawierająca informacje o usługach dodatkowych dostępnych przy rezerwacji sali w systemie.
 * Jej instancje są wykorzystywane w warstwie prezentacji.
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class ExtraServiceDTO {

    private String description;
    private double price;
    private String serviceName;
    private boolean active;

    /**
     * Metoda wyświetlająca słowo tak/nie na podstawie aktywności danej usługi dodatkowej.
     *
     * @return Zinternacjonalizowany ciąg znaków.
     */
    public String getActiveString() {
        if(active)
            return ResourceBundles.getTranslatedText("page.common.yes");
        else return ResourceBundles.getTranslatedText("page.common.no");
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO[name= " + serviceName;
    }
}

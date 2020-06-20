package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa DTO reprezentująca agregat. Jej instancje są wykorzystywane w warstwie prezentacji w celu wyświetlenia
 * łącznej liczby gości na wszystkich imprezach oraz średniej liczby gości na każdej z imprez.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AverageGuestNumberDTO {
    private Long guestSum;
    private Long eventSum;
    private Long average;

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.AverageGuestNumberDTO";
    }
}

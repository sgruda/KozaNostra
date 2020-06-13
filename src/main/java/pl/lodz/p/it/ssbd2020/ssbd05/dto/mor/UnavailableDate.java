package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Obiekt wykorzystywany przy tworzeniu rezerwacji do
 * utworzenia niedostępnych okienek czasowych.
 */
@Data
@AllArgsConstructor
public class UnavailableDate {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

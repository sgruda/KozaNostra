package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UnavailableDate {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

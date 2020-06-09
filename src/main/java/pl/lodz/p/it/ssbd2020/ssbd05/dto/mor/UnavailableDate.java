package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class UnavailableDate {
    private Timestamp startDate;
    private Timestamp endDate;
}

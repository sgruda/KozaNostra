package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;

import java.util.Collection;
import java.util.stream.Collectors;

public class StatusMapper {
    public static Collection<String> toStatusStringCollection(Collection<Status> statusCollection) {
        return statusCollection.stream()
                .map(Status::getStatusName)
                .collect(Collectors.toList());
    }
}

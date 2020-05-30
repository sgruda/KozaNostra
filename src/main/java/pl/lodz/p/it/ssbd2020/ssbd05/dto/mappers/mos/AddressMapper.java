package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO toAddressDTO(Address address);
    Address createNewAddress(AddressDTO addressDTO);
}

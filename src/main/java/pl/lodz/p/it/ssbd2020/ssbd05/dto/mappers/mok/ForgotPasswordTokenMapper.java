package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.ForgotPasswordToken;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITH_SECONDS;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ForgotPasswordTokenMapper {

    ForgotPasswordTokenMapper INSTANCE = Mappers.getMapper(ForgotPasswordTokenMapper.class);

    @Mapping(target = "expireDate", dateFormat = WITH_SECONDS)
    ForgotPasswordTokenDTO toTokenDTO(ForgotPasswordToken forgotPasswordToken);
}

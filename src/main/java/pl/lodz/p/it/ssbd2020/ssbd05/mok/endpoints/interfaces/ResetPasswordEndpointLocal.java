package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

@Local
public interface ResetPasswordEndpointLocal {

    void findByMail(String mail) throws AppBaseException;
    void resetPassword(String mail) throws AppBaseException;
    ForgotPasswordTokenDTO findByHash(String hash) throws AppBaseException;
}

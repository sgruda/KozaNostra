package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
/**
 * Interfejs dla punktu dostępowego ResendActivationEmailEndpointLocal pośredniczącemu
 * przy powtórnym wysyłaniu maila z linkiem aktywacyjnym
 *
 */
@Local
public interface ResendActivationEmailEndpointLocal {
    /**
     * Metoda odpowiedzialna za powtóre wysłanie maila z linkiem aktywacyjnym
     *
     * @param login Login konta użytkownika, do którego ma zostać powtórnie wysłany mail.
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void resendEmail(String login) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
/**
 * Interfejs dla punktu dostępowego ResetPasswordEndpoint pośredniczącemu
 * w resetowaniu hasła użytkownika.
 */
@Local
public interface ResetPasswordEndpointLocal {

    /**
     * Metoda wyszukująca konto z podanym adresem e-mail.
     *
     * @param mail Adres e-mail użytkownika resetującego hasło.
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void findByMail(String mail) throws AppBaseException;
    /**
     * Metoda wyszukująca konto z podanym loginem.
     *
     * @param login Login użytkownika resetującego hasło.
     * @return AccountDTO
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    AccountDTO findByLogin(String login) throws AppBaseException;
    /**
     * Metoda resetująca hasło.
     *
     * @param mail Adres e-mail, na który zostanie wysłany mail z informacją o resecie hasła.
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void resetPassword(String mail) throws AppBaseException;
    /**
     * Metoda wyszukująca token do resetowania hasła poprzez podany skrót hasła.
     *
     * @param hash Skrót szukanego hasła. (SHA256)
     * @return ForgotPasswordToken
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    ForgotPasswordTokenDTO findByHash(String hash) throws AppBaseException;
    /**
     * Metoda zmieniająca zresetowane hasło.
     *
     * @param accountDTO Obiekt klasy AccountDTO reprezentujący konto ze zresetowanym hasłem.
     * @throws AppBaseException Wyjątek aplikacyjny
     */
    void changeResettedPassword(AccountDTO accountDTO) throws AppBaseException;
}

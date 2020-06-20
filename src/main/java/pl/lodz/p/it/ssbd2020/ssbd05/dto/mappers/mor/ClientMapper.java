package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ClientDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;

/**
 * Klasa konwertująca pomiędzy obiektami klas Client i ClientDTO.
 * Jest ona wykorzystywana przez bibliotekę MapStruct w implementacji interfejsu ReservationMapper.
 */
public class ClientMapper {

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy Client na ClientDTO.
     *
     * @param client Obiekt klasy Client.
     * @return Obiekt klasy ClientDTO.
     */
    ClientDTO toClientDTO(Client client) {
        return new ClientDTO(client.getAccount().getLogin(),
                client.getAccount().getFirstname(),
                client.getAccount().getLastname(),
                client.getAccount().getEmail());
    }
}

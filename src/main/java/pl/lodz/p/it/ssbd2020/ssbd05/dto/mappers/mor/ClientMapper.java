package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;


import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ClientDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;

public class ClientMapper {
    ClientDTO toClientDTO(Client client) {
        return new ClientDTO(client.getAccount().getLogin(),
                client.getAccount().getFirstname(),
                client.getAccount().getLastname(),
                client.getAccount().getEmail());
    }
}

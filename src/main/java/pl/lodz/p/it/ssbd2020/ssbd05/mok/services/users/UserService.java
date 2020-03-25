package pl.lodz.p.it.ssbd2020.ssbd05.mok.services.users;

import pl.lodz.p.it.ssbd2020.ssbd05.mok.model.User;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.repositories.users.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class UserService implements Serializable {
    @Inject
    private UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.getUser(username);
    }
}

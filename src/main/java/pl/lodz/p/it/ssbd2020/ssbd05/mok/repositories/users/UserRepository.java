package pl.lodz.p.it.ssbd2020.ssbd05.mok.repositories.users;


import pl.lodz.p.it.ssbd2020.ssbd05.mok.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.LinkedList;


@Named
@ApplicationScoped
public class UserRepository {

    private LinkedList<User> users;

    public synchronized User getUser(String username) {
       for(User user : users) {
           if (user.getUsername().equals(username))
               return user;
       }
        return null;
    }
    @PostConstruct
    private void MockUserDb() {
        users.add(new User("roni1", "passwd1"));
        users.add(new User("admin1", "adminpasswd1"));
    }
}

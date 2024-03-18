package ar.edu.itba.paw;

import ar.edu.itba.paw.services.User;
import ar.edu.itba.paw.services.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(long id) {
        return new User("PAW (id: " + id + ")");
    }
}

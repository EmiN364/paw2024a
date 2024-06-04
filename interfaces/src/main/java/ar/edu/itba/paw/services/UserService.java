package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    User create(String username, String password);

    List<User> listUsers(int page);

    void demoJpaBehaviors();
}

package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

import javax.sql.DataSource;
import java.util.Optional;

public interface UserDao {
    // Data Access Object
    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    User create(final String username, final String password);
}

package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface UserDao {
// Data Access Object
    User findById(long id);
}

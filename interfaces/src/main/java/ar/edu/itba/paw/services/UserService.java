package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface UserService {
    public User findById(long id);
}

package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User create(String username, String password) {
        return userDao.create(username, passwordEncoder.encode(password));
    }
}

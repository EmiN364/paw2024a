package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    // @Cacheable
    @Transactional(readOnly = false)
    @Override
    public User create(String username, String password) {
        LOGGER.atDebug().setMessage("Creating user {}").addArgument(username).log();
        return userDao.create(username, passwordEncoder.encode(password));
    }

    /*@Scheduled(cron = "0 0 0 * * *")
    public void deleteOldUsers() {
        LOGGER.atDebug().setMessage("Deleting old users").log();
        userDao.deleteOldUsers();
    }*/
}

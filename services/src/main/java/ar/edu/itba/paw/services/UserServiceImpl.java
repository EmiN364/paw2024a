package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Issue;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final static int PAGE_SIZE = 10;

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

    @Transactional(readOnly = true)
    @Override
    public void demoJpaBehaviors() {
        /*final User user = userDao.findById(1).get();
        final User other = userDao.findByUsername(user.getUsername()).get();

        user.getReportedIssues().add(new Issue(user, "something is broken and it shouldnt"));

        LOGGER.atDebug().setMessage("Obtained user from different method").log();*/

        final List<User> user = userDao.listAll(2, 1);
    }

    public List<User> listUsers(int page) {
        return userDao.listAll(page, PAGE_SIZE);
    }

    /*@Scheduled(cron = "0 0 0 * * *")
    public void deleteOldUsers() {
        LOGGER.atDebug().setMessage("Deleting old users").log();
        userDao.deleteOldUsers();
    }*/
}

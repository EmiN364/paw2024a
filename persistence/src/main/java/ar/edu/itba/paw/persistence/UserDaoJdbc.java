package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserDao;
import org.springframework.stereotype.Repository;

@Repository
//@Primary
public class UserDaoJdbc implements UserDao {

    @Override
    public User findById(long id) {
        return new User("PAW from DAO (id: " + id + ")");
    }
}

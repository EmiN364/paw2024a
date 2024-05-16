package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public User create(String username, String password) {
        final User user = new User(username, password);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        // JQL - JPA Query Language || HQL - Hibernate Query Language >>>> SQL
        // username no es el nombre de la columna, es el nombre del atributo de la clase
        TypedQuery<User> query = em.createQuery("from User where username = :username", User.class)
                .setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }
}

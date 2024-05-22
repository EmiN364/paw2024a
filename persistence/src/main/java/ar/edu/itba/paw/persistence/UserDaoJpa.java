package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    public List<User> listAll(int page, int pageSize) {
        // 1 + 1
        // 1 - SQL -> obtener los ids de los objetos a retornar
        Query nativeQuery =
                em.createNativeQuery("SELECT userid FROM users2")
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize);

        @SuppressWarnings("unchecked")
        List<Long> idList = ((Stream<Integer>) nativeQuery.getResultStream()).map(Integer::longValue).toList();

        // 2 - JPA -> recuperar los objetos a partir de los ids
        TypedQuery<User> query = em.createQuery("from User where userId IN :ids", User.class);
        query.setParameter("ids", idList);
        return query.getResultList();
    }

    /*private List<User> getFromDB(Query nativeQuery) {
        @SuppressWarnings("unchecked")
        List<Long> idList = ((Stream<Integer>) nativeQuery.getResultStream()).map(Integer::longValue).toList();

        TypedQuery<User> query = em.createQuery("from User where userId IN :ids", User.class);
        query.setParameter("ids", idList);
        return query.getResultList();
    }*/
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
//@Primary
public class UserDaoJdbc implements UserDao {

    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getLong("userid"),
            rs.getString("username"),
            rs.getString("password")
    );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public UserDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds)
                .usingGeneratedKeyColumns("userid")
                .withTableName("users2");

        /* jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS...."); */ // Se movio a schema y se ejecuta en los configs
    }

    @Override
    public Optional<User> findById(long id) {
        /*DataSource ds;

        try (Connection connection = ds.getConnection("", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SQL");
             ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    return new User(rs.getString("username"));
                }

        } catch (SQLException e) {
            throw new RuntimeException(e); // not really a raw runtimeexception 'though
        }*/
        /*jdbcTemplate.query("SELECT * FROM users WHERE userId = '" + id + "';", new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return null;
            }
        });*/

        final List<User> list = jdbcTemplate.query("SELECT * FROM users2 WHERE userid = ?", new Object[] { id } ,ROW_MAPPER);
        return list.stream().findFirst();
    }

    public Optional<User> findByUsername(String username) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users2 WHERE username = ?", new Object[] { username } ,ROW_MAPPER);
        return list.stream().findFirst();
    }


    @Override
    public User create(final String username, final String password) {
        // return runAsTransaction(() -> {
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", username);
            userData.put("password", password);

            Number generatedId = simpleJdbcInsert.executeAndReturnKey(userData);
            return new User(generatedId.longValue(), username, password);
        // });
        // Otra forma, hacer clase inner TxUserDao implements UserDao, definir el runastx, y sobrescribir todas las clases
        // return runastx(() -> realdao.create(username, password));
    }

    /*private <T> T runAsTransaction(final Supplier<T> sup) {
        jdbcTemplate.execute("BEGIN TRANSACTION");
        try {
            final T result = sup.get();
            jdbcTemplate.execute("COMMIT");
            return result;
        } catch (Exception e) {
            jdbcTemplate.execute("ROLLBACK");
            throw e;
        }
        try {
            return sup.get();
        } catch (Exception e) {
            jdbcTemplate.execute("ROLLBACK");
            throw e;
        } finally {
            jdbcTemplate.execute("COMMIT");
        }
    }*/

}

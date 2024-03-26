package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

// @Rollback // Se usa para que no se commiteen los cambios en nuestra db
//@Sql(scripts = "classpath:sql/user.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJdbcTest {

    private static final String USERNAME = "My user";

    @Autowired
    private UserDaoJdbc userDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before // =@BeforeAll
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate() {
        // 1. Precondiciones

        // 2. Ejercito la class under test
        User user = userDao.create(USERNAME);

        // 3. Postcondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testCreateTwo() {
        // 1. Precondiciones

        // 2. Ejercito la class under test
        User user = userDao.create(USERNAME + "2");

        // 3. Postcondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME + "2", user.getUsername());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }
}

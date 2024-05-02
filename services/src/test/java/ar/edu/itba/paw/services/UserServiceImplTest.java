package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final long USER_ID = 1;
    private static final String USERNAME = "My user";
    private static final String PASSWORD = "password123";

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDao userDao;

    // Con el @InjectMocks (equivalente a autowired) y @Mock no necesito mas el constructor
    /*@Before
    public void setUp() {
        userDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImpl(null);
    }*/

    @Test
    public void testFindByIdNonExisting() {
        // 1. Precondiciones
        // Mock por defualt devuelve 0, false, null
        Mockito.when(userDao.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // 2. Ejercita la class under test (Deberia ser una unica linea, mas de una llamada no seria unitario)
        Optional<User> maybeUser = userService.findById(1);

        // 3. Postcondiciones - assertions
        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testFindByIdExistingUser() {
        // 1. Precondiciones
        Mockito.when(userDao.findById(Mockito.eq(USER_ID))).thenReturn(Optional.of(new User(USER_ID, USERNAME, PASSWORD)));

        // 2. Ejercita la class under test
        Optional<User> maybeUser = userService.findById(USER_ID);

        // 3. Postcondiciones - assertions
        Assert.assertNotNull(maybeUser);
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(USER_ID, maybeUser.get().getUserId());
    }

    @Test
    public void testCreateUser() {
        // 1. Precondiciones
        Mockito.when(userDao.create(Mockito.eq(USERNAME), Mockito.anyString())).thenReturn(new User(USER_ID, USERNAME, PASSWORD));

        // 2. Ejercita la class under test
        User user = userService.create(USERNAME, PASSWORD);

        // 3. Postcondiciones - assertions
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getUsername());

        // No esta bueno esto. Estoy testeando la implementacion, no lo que quiero testear (el compartamiento).
        // Habla de un problema de diseño. NO USAR
        // Mockito.verify(userDao, Mockito.only());
        // Mockito.verify(userDao, Mockito.atLeastOnce());
        // Mockito.verify(userDao);
        // Mockito.verifyNoMoreInteractions(userDao);
    }

    // @Test(expected = RuntimeException.class)
}

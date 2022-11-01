package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;



/**
 * Test the user Controller class
 * 
 * @author Team A Bovines - Jakob Langtry
 */
@Tag("Controller-tier")
public class UserControllerTests {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new userController object and inject
     * a mock user DAO
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException {  // getuser may throw IOException
        // Setup
        User user = new User(1, "testUserName", "testPassword", new ArrayList<Order>(), "email");
        // When the same id is passed in, our mock user DAO will return the user object
        when(mockUserDAO.getUser(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // createuser may throw IOException
        // Setup
        int userId = 99;
        // When the same id is passed in, our mock user DAO will return null, simulating
        // no user found
        when(mockUserDAO.getUser(userId)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // createuser may throw IOException
        // Setup
        int userId = 99;
        // When getuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all userController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateUser() throws IOException {  // createuser may throw IOException
        // Setup
        User user = new User(1, "testUserName", "testPassword", new ArrayList<Order>(), "email");
        // when createuser is called, return true simulating successful
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException {  // createuser may throw IOException
        // Setup
        User user = new User(1, "testUserName", "testPassword", new ArrayList<Order>(), "email");
        // when createuser is called, return false simulating failed
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateUserHandleException() throws IOException {  // createuser may throw IOException
        // Setup
        User user = new User(1, "testUserName4", "testPassword4", new ArrayList<Order>(), "email");

        // When createuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).createUser(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws IOException { // updateuser may throw IOException
        // Setup
        User user = new User(1, "TestingUSerName", "thisismypasswordnow", new ArrayList<Order>(), "email");
        // when updateuser is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(user);
        user.setUsername("Bolt");

        // Invoke
        response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testUpdateUserFailed() throws IOException { // updateuser may throw IOException
        // Setup
        User user = new User(1, "blehname", "thisispassword", new ArrayList<Order>(), "email");
        // when updateuser is called, return null simulating unsuccessful
        // update and save
        when(mockUserDAO.updateUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateUserHandleException() throws IOException { // updateuser may throw IOException
        // Setup
        User user = new User(1, "blehnameTwo", "thisispasswordtwo", new ArrayList<Order>(), "email");
        // When updateuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws IOException { // getusers may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User(1, "InstaUser", "InstaPassword", new ArrayList<Order>(), "instaEmail");
        users[1] = new User(1, "CoolUser", "CoolPassword", new ArrayList<Order>(), "coolEmail");
        // When getusers is called return the users created above
        when(mockUserDAO.getUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    public void testGetUsersHandleException() throws IOException { // getusers may throw IOException
        // Setup
        // When getusers is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchUsers() throws IOException { // findusers may throw IOException
        // Setup
        String searchString = "Us";
        User[] users = new User[2];
        users[0] = new User(1, "InstaUser", "InstaPassword", new ArrayList<Order>(), "instaEmail");
        users[1] = new User(1, "CoolUser", "CoolPassword", new ArrayList<Order>(), "coolEmail");
        // When findusers is called with the search string, return the two
        /// users above
        when(mockUserDAO.searchUsers(searchString)).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    public void testSearchUsersHandleException() throws IOException { // findusers may throw IOException
        // Setup
        String searchString = "an";
        // When searchuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).searchUsers(searchString);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException { // deleteuser may throw IOException
        // Setup
        int userId = 99;
        // when deleteuser is called return true, simulating successful deletion
        when(mockUserDAO.deleteUser(userId)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException { // deleteuser may throw IOException
        // Setup
        int userId = 99;
        // when deleteuser is called return false, simulating failed deletion
        when(mockUserDAO.deleteUser(userId)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException { // deleteuser may throw IOException
        // Setup
        int userId = 99;
        // When deleteuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).deleteUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testLogin() throws IOException {
        // Setup
        User user = new User(69, "testUserName", "testPassword", new ArrayList<Order>(), "email");
        // When login is called our mock user DAO will return the user object
        when(mockUserDAO.login(user.getUsername(), user.getPassword())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.login(user.getUsername(), user.getPassword());

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testLoginNotFound() throws IOException {
        // Setup
        User user = new User(69, "testUserName2", "testPassword2", new ArrayList<Order>(), "email");
        // simulate failure
        when(mockUserDAO.login(user.getUsername(), user.getPassword())).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.login(user.getUsername(), user.getPassword());

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testLoginHandleException() throws IOException {
        // Setup
        User user = new User(69, "testUserName3", "testPassword3", new ArrayList<Order>(), "email");
        // simulate ioexception
        doThrow(new IOException()).when(mockUserDAO).login(user.getUsername(), user.getPassword());

        // Invoke
        ResponseEntity<User> response = userController.login(user.getUsername(), user.getPassword());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetCurrentUser() throws IOException {
        // Setup
        User user = new User(69, "testUserName", "testPassword", new ArrayList<Order>(), "email");
        // When we get current user, simulate the return of user
        when(mockUserDAO.getCurrentUser()).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getCurrentUser();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }
    @Test
    public void testGetCurrentUserHandleException() throws IOException {
        // testing a ioexception throw
        doThrow(new IOException()).when(mockUserDAO).getCurrentUser();

        // Invoke
        ResponseEntity<User> response = userController.getCurrentUser();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    @Test
    public void testLogout() throws IOException {
        //Invoke
        userController.logout();
        ResponseEntity<User> response = userController.getGuest();

        //Analyze
        assertEquals(userController.getCurrentUser(), response);
    }
}

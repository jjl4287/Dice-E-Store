package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.controller.UserController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the user Controller class
 * 
 * @author Jakob Langtry
 */
@Tag("Controller-tier")
public class UserControllerTests {
    private StoreController storeController;
    private UserDAO mockuserDAO;

    /**
     * Before each test, create a new userController object and inject
     * a mock user DAO
     */
    @BeforeEach
    public void setupuserController() {
        mockuserDAO = mock(UserDAO.class);
        storeController = new UserController(mockuserDAO);
    }

    @Test s
    public void testGetuser() throws IOException {  // getuser may throw IOException
        // Setup
        User user = new User(1, "D10", 20, 9.99f);
        // When the same id is passed in, our mock user DAO will return the user object
        when(mockuserDAO.getuser(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<user> response = storeController.getuser(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testGetuserNotFound() throws Exception { // createuser may throw IOException
        // Setup
        int userId = 99;
        // When the same id is passed in, our mock user DAO will return null, simulating
        // no user found
        when(mockuserDAO.getuser(userId)).thenReturn(null);

        // Invoke
        ResponseEntity<user> response = storeController.getuser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetuserHandleException() throws Exception { // createuser may throw IOException
        // Setup
        int userId = 99;
        // When getuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockuserDAO).getuser(userId);

        // Invoke
        ResponseEntity<user> response = storeController.getuser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all userController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateuser() throws IOException {  // createuser may throw IOException
        // Setup
        user user = new user(1, "D20", 10, 19.99f);
        // when createuser is called, return true simulating successful
        // creation and save
        when(mockuserDAO.createuser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<user> response = storeController.createuser(user);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testCreateuserFailed() throws IOException {  // createuser may throw IOException
        // Setup
        user user = new user(1, "D12", 15, 14.99f);
        // when createuser is called, return false simulating failed
        // creation and save
        when(mockuserDAO.createuser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<user> response = storeController.createuser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateuserHandleException() throws IOException {  // createuser may throw IOException
        // Setup
        user user = new user(1, "D6", 25, 6.99f);

        // When createuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockuserDAO).createuser(user);

        // Invoke
        ResponseEntity<user> response = storeController.createuser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateuser() throws IOException { // updateuser may throw IOException
        // Setup
        user user = new user(1, "D4", 30, 3.99f);
        // when updateuser is called, return true simulating successful
        // update and save
        when(mockuserDAO.updateuser(user)).thenReturn(user);
        ResponseEntity<user> response = storeController.updateuser(user);
        user.setName("Bolt");

        // Invoke
        response = storeController.updateuser(user);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testUpdateuserFailed() throws IOException { // updateuser may throw IOException
        // Setup
        user user = new user(1, "Super Dice", 5, 29.99f);
        // when updateuser is called, return true simulating successful
        // update and save
        when(mockuserDAO.updateuser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<user> response = storeController.updateuser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateuserHandleException() throws IOException { // updateuser may throw IOException
        // Setup
        user user = new user(1, "Ultra Dice", 3, 34.99f);
        // When updateuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockuserDAO).updateuser(user);

        // Invoke
        ResponseEntity<user> response = storeController.updateuser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetusers() throws IOException { // getusers may throw IOException
        // Setup
        user[] users = new user[2];
        users[0] = new user(1, "Insta-Dice", 10, 17.99f);
        users[1] = new user(1, "Cool Dice", 9, 12.99f);
        // When getusers is called return the users created above
        when(mockuserDAO.getusers()).thenReturn(users);

        // Invoke
        ResponseEntity<user[]> response = storeController.getusers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    public void testGetusersHandleException() throws IOException { // getusers may throw IOException
        // Setup
        // When getusers is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockuserDAO).getusers();

        // Invoke
        ResponseEntity<user[]> response = storeController.getusers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchusers() throws IOException { // findusers may throw IOException
        // Setup
        String searchString = "la";
        user[] users = new user[2];
        users[0] = new user(1, "Insta-Dice", 10, 17.99f);
        users[1] = new user(1, "Cool Dice", 9, 12.99f);
        // When findusers is called with the search string, return the two
        /// users above
        when(mockuserDAO.searchusers(searchString)).thenReturn(users);

        // Invoke
        ResponseEntity<user[]> response = storeController.searchusers(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    public void testSearchusersHandleException() throws IOException { // findusers may throw IOException
        // Setup
        String searchString = "an";
        // When createuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockuserDAO).searchusers(searchString);

        // Invoke
        ResponseEntity<user[]> response = storeController.searchusers(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteuser() throws IOException { // deleteuser may throw IOException
        // Setup
        int userId = 99;
        // when deleteuser is called return true, simulating successful deletion
        when(mockuserDAO.deleteuser(userId)).thenReturn(true);

        // Invoke
        ResponseEntity<user> response = storeController.deleteuser(userId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteuserNotFound() throws IOException { // deleteuser may throw IOException
        // Setup
        int userId = 99;
        // when deleteuser is called return false, simulating failed deletion
        when(mockuserDAO.deleteuser(userId)).thenReturn(false);

        // Invoke
        ResponseEntity<user> response = storeController.deleteuser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteuserHandleException() throws IOException { // deleteuser may throw IOException
        // Setup
        int userId = 99;
        // When deleteuser is called on the Mock user DAO, throw an IOException
        doThrow(new IOException()).when(mockuserDAO).deleteuser(userId);

        // Invoke
        ResponseEntity<user> response = storeController.deleteuser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}

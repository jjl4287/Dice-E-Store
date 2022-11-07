package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * The unit test suite for the UserFileDAO class
 * 
 * @author Team A - Bovines - Jakob Langtry/Brady Self
 */
@Tag("Persistence-tier")
public class UserFileDAOTests {

    private UserFileDAO testUserFileDAO;
    private User[] testUsers;
    private ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupUserController() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User(1, "TestUser1", "testUser1Password", new HashSet<Product>(), "testEmail1");
        testUsers[1] = new User(2, "TestUser2", "testUser2Password",  new HashSet<Product>(), "testEmail2");
        testUsers[2] = new User(3, "TestUser3", "testUser3Password",  new HashSet<Product>(), "testEmail3");

        when(mockObjectMapper
            .readValue(new File("mockUsersFile.txt"),User[].class))
                .thenReturn(testUsers);
        testUserFileDAO = new UserFileDAO("mockUsersFile.txt", mockObjectMapper);
    }

    @Test
    public void testGetUsers() throws IOException {  // getuser may throw IOException
        //Invoke
        User[] users = testUserFileDAO.getUsers();

        //Analyze
        assertEquals(users.length, testUsers.length);
        for (int i = 0; i < users.length; i++) {
            assertEquals(users[i], testUsers[i]);
        }
    }

    @Test
    public void testFindUsers() {
        // Invoke
        User[] users = testUserFileDAO.searchUsers("1");

        // Analyze
        assertEquals(users.length,1);
        assertEquals(users[0],testUsers[0]);
    }
    
    @Test
    public void testGetUser() {
        // Invoke
        User user = testUserFileDAO.getUser(2);

        // Analzye
        assertEquals(user, testUsers[1]);
    }

    @Test
    public void testDeleteUser() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> testUserFileDAO.deleteUser(2),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        assertEquals(testUserFileDAO.users.size(),testUsers.length-1);
    }

    @Test
    public void testCreateUser() {
        //Setup
        User user = new User(4, "TestCreateUser", "TestCreateUserPassword", new HashSet<Product>(), "testCreateEmail");

        //Invoke
        User result = assertDoesNotThrow(() -> testUserFileDAO.createUser(user));

        //Analyze
        assertNotNull(result);
        User actual = testUserFileDAO.getUser(user.getId());
        assertEquals(user, actual);
    }

    @Test
    public void testUpdateUser() {
        //Setup
        User updateUser = new User(1, "TestUpdateUser", "TestUpdateUserPassword",  new HashSet<Product>(), "testEmail");

        //Invoke
        User result = assertDoesNotThrow(() -> testUserFileDAO.updateUser(updateUser));

        //Analyze
        assertNotNull(result);
        User actual = testUserFileDAO.getUser(updateUser.getId());
        assertEquals(updateUser, actual);
    }

    @Test
    public void testLogIn() throws IOException {
        //Invoke
        User user = assertDoesNotThrow(() -> testUserFileDAO.login(testUsers[0].getUsername(), testUsers[0].getPassword()));
        
        //Analyze
        assertNotNull(user);
        User actual = testUserFileDAO.getCurrentUser();
        assertEquals(user, actual);
    }

    @Test
    public void testGetCurrentUser() {
        //Invoke
        User user = assertDoesNotThrow(() -> testUserFileDAO.getCurrentUser());

        //Analyze
        User expected = new User(0, "Guest", "guestPassword",  new HashSet<Product>(), "guestEmail");
        assertEquals(user, expected);
    }

    @Test
    public void testLogInFailed() {
        //Invoke
        assertDoesNotThrow(() -> testUserFileDAO.login(testUsers[0].getUsername(), ""));
        User user = assertDoesNotThrow(() -> testUserFileDAO.login("", ""));
        User user2 = assertDoesNotThrow(() -> testUserFileDAO.login(testUsers[0].getUsername(), ""));

        //Analyze
        assertNull(user);
        assertNull(user2);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(User[].class));

        User user = new User(testUsers[0]);

        assertThrows(IOException.class,
                        () -> testUserFileDAO.createUser(user),
                        "IOException not thrown");
    }

    @Test
    public void testGetUserNotFound() {
        // Invoke
        User user = testUserFileDAO.getUser(98);

        // Analyze
        assertEquals(user,null);
    }

    @Test
    public void testDeleteUserNo() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> testUserFileDAO.deleteUser(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result, false);
        assertEquals(testUserFileDAO.users.size(), testUsers.length);
    }

    @Test
    public void testUpdateUserNotFound() {
        // Setup
        User user = new User(98, "newUser", "password", new HashSet<Product>(), "newEmail");

        // Invoke
        User result = assertDoesNotThrow(() -> testUserFileDAO.updateUser(user),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),User[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new UserFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }

    @Test
    public void testGetShoppingCart() throws IOException {
        //Invoke
        Set<Product> shoppingCart = assertDoesNotThrow(() -> testUserFileDAO.getShoppingCart());

        //Analyze
        assertEquals(shoppingCart, new HashSet<Product>());
    }
}

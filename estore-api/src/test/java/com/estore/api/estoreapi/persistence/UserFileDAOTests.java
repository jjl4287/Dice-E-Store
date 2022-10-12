package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.estore.api.estoreapi.persistence.UserDAO;
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


/**
 * The unit test suite for the UserFileDAO class
 * 
 * @author Team A - Bovines - Jakob Langtry
 */
@Tag("Persistence-tier")
public class UserFileDAOTests {

    private UserFileDAO mockUserFileDAO;

    @BeforeEach
    public void setupUserController() {
        mockUserFileDAO = mock(UserFileDAO.class);
        userFileDAO = new UserFileDAO(null, null)
    }

    @Test
    public void testGetUser() throws IOException {  // getuser may throw IOException
        // Setup
        User user = new User(1, "testUserName", "testPassword");
        // When the same id is passed in, our mock user DAO will return the user object
        when(mockUserFileDAO.getUser(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userFileDAO.getUser(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }
    
}

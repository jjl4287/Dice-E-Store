package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the User class
 * 
 * @author Team A - Bovines - Brady Self 
 */
@Tag("Model-tier")
public class UserTests {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "UserName";
        String expected_password = "Password";

        // Invoke
        User user = new User(expected_id, expected_name, expected_password);

        // Analyze
        assertEquals(expected_id, user.getId());
        assertEquals(expected_name, user.getUserName());
        assertEquals(expected_password, user.getPassword());
    }

    @Test
    public void testName() {
        // Setup
        int init_id = 99;
        String init_name = "UserName";
        String init_password = "Password";
        User user = new User(init_id, init_name, init_password);

        String expected_name = "NewUserName";

        // Invoke
        user.setUserName(expected_name);

        // Analyze
        assertEquals(expected_name, user.getUserName());
    }

    @Test
    public void testPassword() {
        // Setup
        int init_id = 99;
        String init_name = "UserName";
        String init_password = "Password";
        User user = new User(init_id, init_name, init_password);

        String expected_password = "NewPassword";

        // Invoke
        user.setPassword(expected_password);

        // Analyze
        assertEquals(expected_password, user.getPassword());
    }

    @Test
    public void testToString() {
        // Setup
        int init_id = 99;
        String init_name = "UserName";
        String init_password = "Password";
        User user = new User(init_id, init_name, init_password);
        String expected_string = String.format(User.STRING_FORMAT, init_id, init_name, init_password);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
    @Test
    public void testHashCode() {
        // Setup
        int id = 1;
        String userName="test";
        String password= "test";
        User usr= new User(id,userName,password);

        // Analyze
        assertNotNull(usr.hashCode());
    }
    @Test
    public void testequals() {
        // Setup
        int id = 1;
        String userName="test";
        String password= "test";


        User usr = new User(id,userName,password);
        User usr2 = new User(id,userName,password);
        User usr3 = new User(id+1,userName,password);
        User usr4 = new User(id+1,userName+"1",password);
        User usr5 = new User(id+1,userName+"1",password+"1");
        User usr6 = new User(id+1,userName+"1",password+"1");
        Object o = new Object();

        // Analyze
        assertEquals(usr,usr2);
        assertNotEquals(usr2,usr3);
        assertNotEquals(usr3,usr4);
        assertNotEquals(usr4,usr5);
        assertNotEquals(usr3,usr5);
        assertNotEquals(usr2,usr6);
        assertNotEquals(usr,o);
    }
}

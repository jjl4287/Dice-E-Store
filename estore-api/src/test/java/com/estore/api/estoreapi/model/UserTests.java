package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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
        ArrayList<Order> expected_orders = new ArrayList<>();
        String expected_email = "email";
        

        // Invoke
        User user = new User(expected_id, expected_name, expected_password, expected_orders, expected_email);

        // Analyze
        assertEquals(expected_id, user.getId());
        assertEquals(expected_name, user.getUserName());
        assertEquals(expected_password, user.getPassword());
        assertEquals(expected_orders, user.getOrders());
        assertEquals(expected_email, user.getEmail());
    }

    @Test
    public void testName() {
        // Setup
        int init_id = 99;
        String init_name = "UserName";
        String init_password = "Password";
        ArrayList<Order> init_orders = new ArrayList<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_orders, init_email);

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
        ArrayList<Order> init_orders = new ArrayList<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_orders, init_password);

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
        ArrayList<Order> init_orders = new ArrayList<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_orders, init_email);
        String expected_string = String.format(User.STRING_FORMAT, init_id, init_name, init_password);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}

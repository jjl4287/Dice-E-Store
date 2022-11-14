package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        String expected_name = "Username";
        String expected_password = "Password";
        Set<Product> expected_shoppingCart = new HashSet<>();
        String expected_email = "email";

        // Invoke
        User user = new User(expected_id, expected_name, expected_password, expected_shoppingCart, expected_email);

        // Analyze
        assertEquals(expected_id, user.getId());
        assertEquals(expected_name, user.getUsername());
        assertEquals(expected_password, user.getPassword());
        assertEquals(expected_shoppingCart, user.getShoppingCart());
        assertEquals(expected_email, user.getEmail());
    }

    @Test
    public void testName() {
        // Setup
        int init_id = 99;
        String init_name = "Username";
        String init_password = "Password";
        Set<Product> init_shoppingCart = new HashSet<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_shoppingCart, init_email);

        String expected_name = "NewUserName";

        // Invoke
        user.setUsername(expected_name);

        // Analyze
        assertEquals(expected_name, user.getUsername());
    }

    @Test
    public void testAddToCart() {
        // Setup
        int init_id = 99;
        String init_name = "Username";
        String init_password = "Password";
        Set<Product> init_shoppingCart = new HashSet<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_shoppingCart, init_email);
        Product product1 = new Product(0, "testProduct1", 1, 9.99f, "url", "description");
        Product product2 = new Product(0, "testProduct2", 2, 19.99f, "url", "description");

        HashSet<Product> expected_products = new HashSet<>();
        expected_products.add(product1);
        expected_products.add(product2);
        expected_products.add(product2);

        // Invoke
        user.addToCart(product1);
        user.addToCart(product2);
        user.addToCart(product2);

        // Analyze
        assertEquals(user.getShoppingCart(), expected_products);
    }

    @Test
    public void testReduceFromCart() {
        // Setup
        int init_id = 99;
        String init_name = "Username";
        String init_password = "Password";
        Set<Product> init_shoppingCart = new HashSet<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_shoppingCart, init_email);
        Product product2 = new Product(0, "testProduct2", 1, 19.99f, "url", "description");

        HashSet<Product> expected_products = new HashSet<>();
        user.addToCart(product2);
        user.addToCart(product2);

        // Invoke
        user.reduceFromCart(product2);
        user.reduceFromCart(product2);

        // Analyze
        assertEquals(user.getShoppingCart(), expected_products);
    }

    @Test
    public void testRemoveFromCart() {
        // Setup
        int init_id = 99;
        String init_name = "Username";
        String init_password = "Password";
        Set<Product> init_shoppingCart = new HashSet<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_shoppingCart, init_email);
        Product product1 = new Product(0, "testProduct1", 1, 9.99f, "url", "description");
        Product product2 = new Product(1, "testProduct2", 2, 19.99f, "url", "description");

        HashSet<Product> expected_products = new HashSet<>();
        expected_products.add(product1);
        expected_products.add(product2);
        expected_products.remove(product1);

        // Invoke
        user.addToCart(product1);
        user.addToCart(product2);
        user.removeFromCart(product1);

        // Analyze
        assertEquals(user.getShoppingCart(), expected_products);
    }

    @Test
    public void testEmail() {
        // Setup
        int init_id = 99;
        String init_name = "Username";
        String init_password = "Password";
        Set<Product> init_shoppingCart = new HashSet<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_shoppingCart, init_email);

        String expected_email = "newEmail";

        // Invoke
        user.setEmail(expected_email);

        // Analyze
        assertEquals(expected_email, user.getEmail());
    }

    @Test
    public void testPassword() {
        // Setup
        int init_id = 99;
        String init_name = "UserName";
        String init_password = "Password";
        Set<Product> init_shoppingCart = new HashSet<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_shoppingCart, init_password);

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
        String init_name = "Username";
        String init_password = "Password";
        Set<Product> init_shoppingCart = new HashSet<>();
        String init_email = "email";
        User user = new User(init_id, init_name, init_password, init_shoppingCart, init_email);
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
        String username = "test";
        String password = "test";
        Set<Product> shoppingCart = new HashSet<>();
        String email = "test";
        User usr = new User(id, username, password, shoppingCart, email);

        // Analyze
        assertNotNull(usr.hashCode());
    }

    @Test
    public void testequals() {
        // Setup
        int id = 1;
        String username = "test";
        String password = "test";
        Set<Product> shoppingCart = new HashSet<>();
        String email = "test";

        User usr = new User(id, username, password, shoppingCart, email);
        User usr2 = new User(id, username, password, shoppingCart, email);
        User usr3 = new User(id + 1, username, password, shoppingCart, email);
        User usr4 = new User(id + 1, username + "1", password, shoppingCart, email);
        User usr5 = new User(id + 1, username + "1", password + "1", shoppingCart, email);
        User usr6 = new User(id + 1, username + "1", password + "1", shoppingCart, email);
        Object o = new Object();

        // Analyze
        assertEquals(usr, usr2);
        assertNotEquals(usr2, usr3);
        assertNotEquals(usr3, usr4);
        assertNotEquals(usr4, usr5);
        assertNotEquals(usr3, usr5);
        assertNotEquals(usr2, usr6);
        assertNotEquals(usr, o);
    }
}

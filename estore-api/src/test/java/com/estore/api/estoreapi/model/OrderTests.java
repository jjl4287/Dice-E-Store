package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.*;

/**
 * The unit test suite for the Order class
 * 
 * @author Team A - Bovines - Maximo Bustillo
 */
@Tag("Model-tier")
public class OrderTests {
    @Test
    public void testCtor() {
        // Setup
        int id = 1;
        String userName = "test";
        String password = "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User expected_usr = new User(id, userName, password, shoppingCart, email);
        Set<Product> expected_purchase = new HashSet<>();
        UUID expected_uuid = UUID.randomUUID();
        boolean expected_fulfillment = false;
        for (int i = 0; i < 5; i++) {
            expected_purchase.add(new Product(i, "p" + i, 25 - i, 25.99f, "url", "desc"));
        }

        // Invoke
        Order order1 = new Order(expected_purchase, expected_usr);
        Order order2 = new Order(expected_purchase, expected_usr, expected_uuid, false);

        // Analyze
        assertEquals(expected_purchase, order1.getProducts());
        assertEquals(expected_usr, order1.getUser());
        assertEquals(expected_fulfillment, order1.getFulfilled());
        assertNotNull(order1.getUuid());

        assertEquals(expected_purchase, order2.getProducts());
        assertEquals(expected_usr, order2.getUser());
        assertEquals(expected_fulfillment, order2.getFulfilled());
        assertEquals(expected_uuid, order2.getUuid());
    }

    @Test
    public void testFullfillment() {
        // Setup
        int id = 1;
        String userName = "test";
        String password = "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User usr = new User(id, userName, password, shoppingCart, email);
        Set<Product> purchase = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            purchase.add(new Product(i, "p" + i, 25 - i, 25.99f, "url", "desc"));
        }

        Order order = new Order(purchase, usr);

        boolean expected_fulfillment = true;

        // invoke
        order.fulfillOrder();

        // Analyze
        assertEquals(expected_fulfillment, order.getFulfilled());
    }

    @Test
    public void testEquality() {
        // Setup
        int id = 1;
        String userName = "test";
        String password = "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User usr = new User(id, userName, password, shoppingCart, email);
        Set<Product> purchase = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            purchase.add(new Product(i, "p" + i, 25 - i, 25.99f, "url", "desc"));
        }

        // create
        Order order = new Order(purchase, usr);
        Order order2 = new Order(purchase, usr);
        Order order3 = new Order(purchase, usr, order.getUuid(), false);
        Product p = new Product(1, "test", 1, 1, "url", "desc");

        // Analyze
        assertEquals(order, order);
        assertNotEquals(order, order2);
        assertEquals(order, order3);
        assertNotEquals(order, p);
    }

    @Test
    public void testToString() {
        // Setup
        int id = 1;
        String userName = "test";
        String password = "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User usr = new User(id, userName, password, shoppingCart, email);
        Set<Product> purchase = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            purchase.add(new Product(i, "p" + i, 25 - i, 25.99f, "url", "desc"));
        }
        UUID uuid = UUID.randomUUID();
        String expected_string = String.format(Order.STRING_FORMAT, purchase, usr, uuid);

        Order order = new Order(purchase, usr, uuid, false);

        // Invoke
        String actual_string = order.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

    @Test
    public void testHashCode() {
        // Setup
        int id = 1;
        String userName = "test";
        String password = "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User usr = new User(id, userName, password, shoppingCart, email);
        Set<Product> purchase = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            purchase.add(new Product(i, "p" + i, 25 - i, 25.99f, "url", "desc"));
        }

        Order order = new Order(purchase, usr);

        // Analyze
        assertNotNull(order.hashCode());
    }

}
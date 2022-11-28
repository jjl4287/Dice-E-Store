package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Unit tests for OrderFileDAO
 *
 * @author Team A - Bovines - Maximo Bustillo
 */
@Tag("Persistence-tier")
public class OrderFileDAOTests {
    OrderDAO testOrderFileDAO;
    Order[] testOrders;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupOrderFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        User u1 = new User(1, "u1", "u1", null, "Email.com");
        User u2 = new User(2, "u2", "u2", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3, "url", "desc");
        Product p2 = new Product(2, "test2", 2, 3, "Url", "desc");

        Set<Product> s1 = new HashSet<>();
        s1.add(p2);
        Set<Product> s2 = new HashSet<>();
        s2.add(p2);
        s2.add(p1);

        testOrders = new Order[3];
        testOrders[0] = new Order(s1, u1);
        testOrders[1] = new Order(s2, u2);
        testOrders[2] = new Order(s2, u1);

        when(mockObjectMapper
                .readValue(new File("mockOrdersFile.txt"), Order[].class))
                .thenReturn(testOrders);
        testOrderFileDAO = new OrderFileDAO("mockOrdersFile.txt", mockObjectMapper);
    }

    @Test
    public void testGetOrders() throws IOException {  // getuser may throw IOException
        //Invoke
        Order[] orders = testOrderFileDAO.getallOrders();

        Set<Order> s1 = new HashSet<>();
        Set<Order> s2 = new HashSet<>();

        // Analyze
        assertEquals(orders.length, testOrders.length);
        for (int i = 0; i < orders.length; i++) {
            s1.add(orders[i]);
            s2.add(testOrders[i]);
        }
        assertEquals(s1, s2);
    }

    @Test
    public void testFindOrders() throws IOException { // searchOrders may throw IOException
        // Invoke

        User u2 = new User(2, "u2", "u2", null, "Email.com");
        Order[] orders = testOrderFileDAO.findOrders(u2);
        Order[] failedSearch = testOrderFileDAO.findOrders(new User(-2,"u3","u3",null,"email"));

        // Analyze
        assertEquals(orders.length, 1);
        assertEquals(orders[0], orders[0]);
        assertNull(failedSearch);
    }

    @Test
    public void updateOrder() throws IOException { // searchOrders may throw IOException
        // Invoke
        testOrders[0].fulfillOrder();
        Order update = testOrderFileDAO.updateOrder(testOrders[0]);
        Order newOrder = new Order(testOrders[0].getProducts(),testOrders[0].getUser());
        Order failedUpdate = testOrderFileDAO.updateOrder(newOrder);

        // Analyze
        assertEquals(update, testOrders[0]);
        assertNull(failedUpdate);
    }

    @Test
    public void testGetOrder() throws IOException { // getOrders may throw IOException
        // Invoke
        Order order = testOrderFileDAO.getOrderbyID(testOrders[1].getUuid());

        // Analzye
        assertEquals(order, testOrders[1]);
    }

    @Test
    public void testCreateOrder() throws IOException { // createOrder may throw IOException
        // Setup

        User u1 = new User(1, "u1", "u1", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3, "url", "desc");
        Product p2 = new Product(2, "test2", 2, 3, "url", "desc");

        Set<Product> s1 = new HashSet<>();
        s1.add(p1);
        s1.add(p2);

        Order order = new Order(s1, u1);

        //Invoke
        Order result = assertDoesNotThrow(() -> testOrderFileDAO.createNewOrder(order));

        // Analyze
        assertNotNull(result);
        Order actual = testOrderFileDAO.getOrderbyID(order.getUuid());
        assertEquals(order, actual);
    }


    @Test
    public void testGetOrderNotFound() throws IOException {
        // Invoke
        Order order = testOrderFileDAO.getOrderbyID(UUID.randomUUID());

        // Analyze
        assertEquals(order, null);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Order[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new OrderFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }
}

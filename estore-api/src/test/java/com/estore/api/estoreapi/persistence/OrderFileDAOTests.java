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
 * @author Team A - Bovines - Vincent Sarubbi
 */
@Tag("Persistence-tier")
public class OrderFileDAOTests {
    GenericDAO<Order,UUID> testOrderFileDAO;
    Order[] testOrders;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupOrderFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        User u1 = new User(1, "u1", "u1", null, "Email.com");
        User u2 = new User(2, "u2", "u2", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3);
        Product p2 = new Product(2, "test2", 2, 3);


        Set<Product> s1 = new HashSet<>();
        s1.add(p2);
        Set<Product> s2 = new HashSet<>();
        s2.add(p2);
        s2.add(p1);

        testOrders = new Order[3];
        testOrders[0] = new Order(s1,u1);
        testOrders[1] = new Order(s2,u2);
        testOrders[2] = new Order(s2,u1);

        when(mockObjectMapper
            .readValue(new File("mockOrdersFile.txt"),Order[].class))
                .thenReturn(testOrders);
        testOrderFileDAO = new OrderFileDAO("mockOrdersFile.txt",mockObjectMapper);
    }

    @Test
    public void testGetOrders() throws IOException {  // getuser may throw IOException
        //Invoke
        Order[] orders = testOrderFileDAO.getall();

        
        Set<Order> s1 = new HashSet<>();
        Set<Order> s2 = new HashSet<>();

        //Analyze
        assertEquals(orders.length, testOrders.length);
        for(int i =0;i<orders.length;i++){
            s1.add(orders[i]);
            s2.add(testOrders[i]);
        }
        assertEquals(s1, s2);
    }

    @Test
    public void testFindOrders() throws IOException { //searchOrders may throw IOException
        // Invoke

        User u2 = new User(2, "u2", "u2", null, "Email.com");

        Order[] orders = testOrderFileDAO.search(u2);

        // Analyze
        assertEquals(orders.length,1);
        assertEquals(orders[0],orders[0]);
    }

    @Test
    public void testGetOrder() throws IOException { //getOrders may throw IOException
        // Invoke
        Order order = testOrderFileDAO.getbyID(testOrders[1].getUuid());

        // Analzye
        assertEquals(order, testOrders[1]);
    }

    @Test
    public void testCreateOrder() throws IOException { //createOrder may throw IOException
        //Setup

        User u1 = new User(1, "u1", "u1", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3);
        Product p2 = new Product(2, "test2", 2, 3);


        Set<Product> s1 = new HashSet<>();
        s1.add(p1);
        s1.add(p2);


        Order order = new Order(s1,u1);

        //Invoke
        Order result = assertDoesNotThrow(() -> testOrderFileDAO.createNew(order));

        //Analyze
        assertNotNull(result);
        Order actual = testOrderFileDAO.getbyID(order.getUuid());
        assertEquals(order, actual);
    }

    // @Test
    // public void testUpdateOrder() throws IOException {
    //     //Setup

    //     User u1 = new User(1, "u1", "u1", null, "Email.com");

    //     Product p1 = new Product(1, "test", 2, 3);
    //     Product p2 = new Product(2, "test2", 2, 3);


    //     Set<Product> s1 = new HashSet<>();
    //     s1.add(p1,p2);


    //     Order updateOrder = new Order(s1,u1);

    //     //Invoke
    //     Order result = assertDoesNotThrow(() -> testOrderFileDAO.updateValue(updateOrder));

    //     //Analyze
    //     assertNotNull(result);
    //     Order actual = testOrderFileDAO.getOrder(updateOrder.getId());
    //     assertEquals(updateOrder, actual);
    // }

    // @Test
    // public void testDeleteOrder() {
    //     // Invoke
    //     boolean result = assertDoesNotThrow(() -> testOrderFileDAO.deleteOrder(2),
    //                         "Unexpected exception thrown");

    //     // Analzye
    //     assertEquals(result,true);
    // }

    @Test
    public void testGetOrderNotFound() throws IOException {
        // Invoke
        Order order = testOrderFileDAO.getbyID(UUID.randomUUID());

        // Analyze
        assertEquals(order,null);
    }

    // @Test
    // public void testDeleteOrderNo() {
    //     // Invoke
    //     boolean result = assertDoesNotThrow(() -> testOrderFileDAO.deleteOrder(69),
    //                                             "Unexpected exception thrown");

    //     // Analyze
    //     assertEquals(result, false);
    // }

    // @Test
    // public void testUpdateOrderNotFound() {
    //     // Setup
    //     Order order = new Order(69, "newOrder", 69, 69);

    //     // Invoke
    //     Order result = assertDoesNotThrow(() -> testOrderFileDAO.updateOrder(order),
    //                                             "Unexpected exception thrown");

    //     // Analyze
    //     assertNull(result);
    // }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Order[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new OrderFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}

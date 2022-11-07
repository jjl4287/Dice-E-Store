package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.estore.api.estoreapi.persistence.GenericDAO;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.OrderDTO;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Order Controller class
 * 
 * @author Team A Bovines - Chris Ferioli, Vincent Sarubbi
 */
@Tag("Controller-tier")
public class OrderControllerTests {
    private OrderController orderController;
    private GenericDAO<Order,UUID> mockOrderDAO;
    private UUID testUUID = UUID.randomUUID();
    

    /**
     * Before each test, create a new OrderController object and inject
     * a mock Order DAO
     */
    @BeforeEach
    public void setupOrderController() {
        mockOrderDAO = mock(GenericDAO.class);
        orderController = new OrderController(mockOrderDAO);
    }

    @Test
    public void testGetOrder() throws IOException {  // getOrder may throw IOException
        // Setup

        User u1 = new User(1, "u1", "u1", null, "Email.com");
        
        Product p1 = new Product(1, "test", 2, 3);


        Set<Product> s1 = new HashSet<>();
        s1.add(p1);


        Order order = new Order(s1,u1,testUUID);
        // When the same id is passed in, our mock Order DAO will return the Order object
        when(mockOrderDAO.getbyID(order.getUuid())).thenReturn(order);

        // Invoke
        ResponseEntity<Order> response = orderController.getOrder(order.getUuid());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(order,response.getBody());
    }

    @Test
    public void testGetOrderNotFound() throws Exception { // createOrder may throw IOException
        // Setup
        UUID orderId = UUID.randomUUID();
        // When the same id is passed in, our mock Order DAO will return null, simulating
        // no order found
        when(mockOrderDAO.getbyID(orderId)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.getOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetOrderHandleException() throws Exception { // createOrder may throw IOException
        // Setup
        UUID orderId = UUID.randomUUID();
        // When getOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).getbyID(orderId);

        // Invoke
        ResponseEntity<Order> response = orderController.getOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all OrderController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateOrder() throws IOException {  // createOrder may throw IOException
        // Setup

        User u1 = new User(1, "u1", "u1", null, "Email.com");
        
        Product p1 = new Product(1, "test", 2, 3);


        ArrayList<Product> a1 = new ArrayList<>();
        a1.add(p1);

        OrderDTO orderdto = new OrderDTO(a1, u1);
        Order order = new Order(orderdto);
        // when createOrder is called, return true simulating successful
        // creation and save
        when(mockOrderDAO.createNew(order)).thenReturn(order);

        // Invoke
        ResponseEntity<Order> response = orderController.createOrder(orderdto);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(order,response.getBody());
    }

    @Test
    public void testCreateOrderFailed() throws IOException {  // createOrder may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        
        Product p1 = new Product(1, "test", 2, 3);

        ArrayList<Product> a1 = new ArrayList<>();
        a1.add(p1);

        OrderDTO orderdto = new OrderDTO(a1, u1);
        Order order = new Order(orderdto);
        // when createOrder is called, return false simulating failed
        // creation and save
        when(mockOrderDAO.createNew(order)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.createOrder(orderdto);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateOrderHandleException() throws IOException {  // createOrder may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        
        Product p1 = new Product(1, "test", 2, 3);


        ArrayList<Product> a1 = new ArrayList<>();
        a1.add(p1);

        OrderDTO orderdto = new OrderDTO(a1, u1);
        Order order = new Order(orderdto);

        // When createOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).createNew(order);

        // Invoke
        ResponseEntity<Order> response = orderController.createOrder(orderdto);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetOrders() throws IOException { // getOrders may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        User u2 = new User(2, "u2", "u2", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3);
        Product p2 = new Product(2, "test2", 2, 3);


        Set<Product> s1 = new HashSet<>();
        s1.add(p2);
        Set<Product> s2 = new HashSet<>();
        s2.add(p2);
        s2.add(p1);

        Order[] testOrders = new Order[2];
        testOrders[0] = new Order(s1,u1);
        testOrders[1] = new Order(s2,u2);
        // When getOrders is called return the orders created above
        when(mockOrderDAO.getall()).thenReturn(testOrders);

        // Invoke
        ResponseEntity<Order[]> response = orderController.getOrders();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testOrders,response.getBody());
    }

    @Test
    public void testGetOrdersHandleException() throws IOException { // getOrders may throw IOException
        // Setup
        // When getOrders is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).getall();

        // Invoke
        ResponseEntity<Order[]> response = orderController.getOrders();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchOrders() throws IOException { // findOrders may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3);
        Product p2 = new Product(2, "test2", 2, 3);


        Set<Product> s1 = new HashSet<>();
        s1.add(p2);
        Set<Product> s2 = new HashSet<>();
        s2.add(p2);
        s2.add(p1);

        Order[] testOrders = new Order[2];
        testOrders[0] = new Order(s1,u1);
        testOrders[1] = new Order(s2,u1);
        // When findOrders is called with the search string, return the two
        /// orders above
        when(mockOrderDAO.search(u1)).thenReturn(testOrders);

        // Invoke
        ResponseEntity<Order[]> response = orderController.searchOrders(u1);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testOrders,response.getBody());
    }


    @Test
    public void testSearchOrdersHandleException() throws IOException { // findOrders may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        // When createOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).search(u1);

        // Invoke
        ResponseEntity<Order[]> response = orderController.searchOrders(u1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}

package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.estore.api.estoreapi.persistence.OrderDAO;
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
 * @author Team A - Bovines - Maximo Bustillo, Vincent Sarubbi
 */
@Tag("Controller-tier")
public class OrderControllerTests {
    private OrderController orderController;
    private OrderDAO mockOrderDAO;
    private UUID testUUID = UUID.randomUUID();
    

    /**
     * Before each test, create a new OrderController object and inject
     * a mock Order DAO
     */
    @BeforeEach
    public void setupOrderController() {
        mockOrderDAO = mock(OrderDAO.class);
        orderController = new OrderController(mockOrderDAO);
    }

    @Test
    public void testGetOrder() throws IOException {  // getOrder may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        Product p1 = new Product(1, "test", 2, 3,"url","desc");
        Set<Product> s1 = new HashSet<>();
        s1.add(p1);
        Order order = new Order(s1,u1,testUUID,false);
        OrderDTO orderdto = new OrderDTO(order);

        // When the same id is passed in, our mock Order DAO will return the Order object
        when(mockOrderDAO.getOrderbyID(order.getUuid())).thenReturn(order);

        // Invoke
        ResponseEntity<OrderDTO> response = orderController.getOrder(order.getUuid());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(orderdto,response.getBody());
    }

    @Test
    public void testGetOrderNotFound() throws Exception { // createOrder may throw IOException
        // Setup
        UUID orderId = UUID.randomUUID();
        // When the same id is passed in, our mock Order DAO will return null, simulating
        // no order found
        when(mockOrderDAO.getOrderbyID(orderId)).thenReturn(null);

        // Invoke
        ResponseEntity<OrderDTO> response = orderController.getOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetOrderHandleException() throws Exception { // createOrder may throw IOException
        // Setup
        UUID orderId = UUID.randomUUID();
        // When getOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).getOrderbyID(orderId);

        // Invoke
        ResponseEntity<OrderDTO> response = orderController.getOrder(orderId);

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
        Product p1 = new Product(1, "test", 2, 3,"url","desc");

        ArrayList<Product> a1 = new ArrayList<>();
        a1.add(p1);

        OrderDTO orderdto = new OrderDTO(a1, u1);
        Order order = new Order(orderdto);

        // when createOrder is called, return true simulating successful
        // creation and save
        when(mockOrderDAO.createNewOrder(order)).thenReturn(order);

        // Invoke
        ResponseEntity<OrderDTO> response = orderController.createOrder(orderdto);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(orderdto,response.getBody());
    }

    @Test
    public void testCreateOrderFailed() throws IOException {  // createOrder may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        Product p1 = new Product(1, "test", 2, 3,"url","desc");

        ArrayList<Product> a1 = new ArrayList<>();
        a1.add(p1);

        OrderDTO orderdto = new OrderDTO(a1, u1);
        Order order = new Order(orderdto);
        // when createOrder is called, return false simulating failed
        // creation and save
        when(mockOrderDAO.createNewOrder(order)).thenReturn(null);

        // Invoke
        ResponseEntity<OrderDTO> response = orderController.createOrder(orderdto);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateOrderHandleException() throws IOException {  // createOrder may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        Product p1 = new Product(1, "test", 2, 3,"url","desc");


        ArrayList<Product> a1 = new ArrayList<>();
        a1.add(p1);

        OrderDTO orderdto = new OrderDTO(a1, u1);
        Order order = new Order(orderdto);

        // When createOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).createNewOrder(order);

        // Invoke
        ResponseEntity<OrderDTO> response = orderController.createOrder(orderdto);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetOrders() throws IOException { // getOrders may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        User u2 = new User(2, "u2", "u2", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3,"url","desc");
        Product p2 = new Product(2, "test2", 2, 3,"url","desc");


        Set<Product> s1 = new HashSet<>();
        s1.add(p2);
        Set<Product> s2 = new HashSet<>();
        s2.add(p2);
        s2.add(p1);

        Order[] testOrders = new Order[2];
        testOrders[0] = new Order(s1,u1);
        testOrders[1] = new Order(s2,u2);
        OrderDTO[] testdto = new OrderDTO[2];
        testdto[0] = new OrderDTO(testOrders[0]);
        testdto[1] = new OrderDTO(testOrders[1]);

        // When getOrders is called return the orders created above
        when(mockOrderDAO.getallOrders()).thenReturn(testOrders);

        // Invoke
        ResponseEntity<OrderDTO[]> response = orderController.getOrders();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertArrayEquals(testdto,response.getBody());
    }

    @Test
    public void testGetOrdersHandleException() throws IOException { // getOrders may throw IOException
        // Setup
        // When getOrders is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).getallOrders();

        // Invoke
        ResponseEntity<OrderDTO[]> response = orderController.getOrders();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchOrders() throws IOException { // findOrders may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");

        Product p1 = new Product(1, "test", 2, 3,"url","desc");
        Product p2 = new Product(2, "test2", 2, 3,"url","desc");


        Set<Product> s1 = new HashSet<>();
        s1.add(p2);
        Set<Product> s2 = new HashSet<>();
        s2.add(p2);
        s2.add(p1);

        Order[] testOrders = new Order[2];
        testOrders[0] = new Order(s1,u1);
        testOrders[1] = new Order(s2,u1);
        OrderDTO[] testdto = new OrderDTO[2];
        testdto[0] = new OrderDTO(testOrders[0]);
        testdto[1] = new OrderDTO(testOrders[1]);
        // When findOrders is called with the search string, return the two
        /// orders above
        when(mockOrderDAO.findOrders(u1)).thenReturn(testOrders);

        // Invoke
        ResponseEntity<OrderDTO[]> response = orderController.searchOrders(u1);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertArrayEquals(testdto,response.getBody());
    }


    @Test
    public void testSearchOrdersHandleException() throws IOException { // findOrders may throw IOException
        // Setup
        User u1 = new User(1, "u1", "u1", null, "Email.com");
        // When createOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).findOrders(u1);

        // Invoke
        ResponseEntity<OrderDTO[]> response = orderController.searchOrders(u1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testFulfillOrder() throws IOException {
        //Setup
        boolean testIsFulfilled = true;
        Order testOrder = new Order(new HashSet<Product>(), new User(1, "u1", "u1", null, "swen261.bovine@gmail.com"), new UUID(0, 0), false);
        OrderDTO testOrderDTO = new OrderDTO(testOrder);
        Order expectedOrder = new Order(testOrderDTO);
        expectedOrder.fulfillOrder();

        //When updateOrder is called return the fullfillment value updated above 
        when(mockOrderDAO.updateOrder(new Order(testOrderDTO))).thenReturn(expectedOrder);

        //Invoke
        ResponseEntity<OrderDTO> response = this.orderController.fulfillOrder(testOrderDTO);

        //Analyze
        assertEquals(response.getBody().getFulfilled(), testIsFulfilled);
    }

    @Test
    public void testFulfillOrderNotFound() throws IOException {
        //Setup
        Order testOrder = new Order(new HashSet<Product>(), new User(1, "u1", "u1", null, "email.com"), new UUID(0, 0), false);
        OrderDTO testOrderDTO = new OrderDTO(testOrder);
        Order expectedOrder = new Order(testOrderDTO);
        expectedOrder.fulfillOrder();

        //When updateOrder is called return internal service error at not finding order
        when(mockOrderDAO.updateOrder(new Order(testOrderDTO))).thenReturn(expectedOrder);

        //Invoke
        ResponseEntity<OrderDTO> response = this.orderController.fulfillOrder(testOrderDTO);

        //Analyze
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testFulfillOrderIOException() throws IOException { // findOrders may throw IOException
        // Setup
        Order testOrder = new Order(new HashSet<Product>(), new User(1, "u1", "u1", null, "email.com"), new UUID(0, 0), false);
        OrderDTO testOrderDTO = new OrderDTO(testOrder);

        // When createOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).updateOrder(new Order(testOrderDTO));

        // Invoke
        ResponseEntity<OrderDTO> response = orderController.fulfillOrder(testOrderDTO);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}

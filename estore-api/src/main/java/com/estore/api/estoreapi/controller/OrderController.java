package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.persistence.OrderDAO;
import com.estore.util.sendEmail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.OrderDTO;
import com.estore.api.estoreapi.model.User;

import java.util.*;


/**
 * Handles REST API requests for the Order resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Team A - Maximo Bustillo
 */

@RestController
@RequestMapping("orders")
public class OrderController {
    private static final Logger LOG = Logger.getLogger(StoreController.class.getName());
    private OrderDAO orderDAO;

    /**
     * Creates a REST API controller to reponds to requests for orders
     * 
     * @param orderDAO The Order Data Access Object to perform CRUD operations
     *                 <br>
     *                 This dependency is injected by the Spring Framework
     */
    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    /**
     * Responds to GET request for a order for the given a UUID
     * 
     * @param id The id of the order
     * 
     * @return ResponseEntity with order object and HTTP status of OK if found
     *         <br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable UUID id) {
        LOG.info("GET /orders/" + id);
        try {
            Order order = orderDAO.getOrderbyID(id);
            if (order != null)
            //order fouund
            //convert to DTO
                return new ResponseEntity<OrderDTO>(new OrderDTO(order),HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all orders
     * 
     * @return ResponseEntity with array of order objects (may be empty) and
     *         HTTP status of OK
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<OrderDTO[]> getOrders() {
        LOG.info("GET /orders");
        try {
            //get all the orders
            Order[] array = orderDAO.getallOrders();
            //prepare a DTO array to send them
            OrderDTO[] dto = new OrderDTO[array.length];
            for (int i =0;i<array.length;i++) {
                //transfer the orders to DTO
                dto[i]=new OrderDTO(array[i]);
                
            }
            return new ResponseEntity<OrderDTO[]>(dto, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all orders whose name contains
     * the same User
     * 
     * @param user The user parameter which contains the user used to find the
     *             orders
     * 
     * @return ResponseEntity with array of orders objects (may be empty) and
     *         HTTP status of OK
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all orders that contain the text "nameHere"
     *         GET http://localhost:8080/orders/?name=nameHere
     */
    @PostMapping("/")
    public ResponseEntity<OrderDTO[]> searchOrders(@RequestBody User user) {
        LOG.info("GET /orders/?name=" + user);

        try {
            //find the users
            Order[] array = orderDAO.findOrders(user);

            //transfer them to DTO array
            OrderDTO[] dto = new OrderDTO[array.length];
            for (int i = 0; i < array.length; i++) {
                dto[i] = new OrderDTO(array[i]);
                System.out.println(dto[i]);
            }
            return new ResponseEntity<OrderDTO[]>(dto, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a order with the provided order object
     * 
     * @param order - The order to create
     * 
     * @return ResponseEntity with created orders object and HTTP status of CREATED
     *         <br>
     *         ResponseEntity with HTTP status of CONFLICT if orders object already
     *         exists
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO dto) {
        //translate orderDTO into order
        Order order = new Order(dto);
        LOG.info("POST /orders " + order);

        try {
            //create the order
            Order created = orderDAO.createNewOrder(order);
            if(created != null) {
                return new ResponseEntity<OrderDTO>(new OrderDTO(created), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * updates an orders fullfillment status
     * 
     * @param order - The order to fulfill
     * 
     * @return ResponseEntity with created orders object and HTTP status of CREATED
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR if mail not sent
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/fulfill")
    public ResponseEntity<OrderDTO> fulfillOrder(@RequestBody OrderDTO order) {
        LOG.info("POST /fulfillorders " + order);
        try {
            //update the order in the DAO and get the original
            Order updatedOrder = orderDAO.updateOrder(new Order(order));
            //make the updated order a DTO
            OrderDTO updatedDTO = new OrderDTO(updatedOrder);
            //try and send the emailS
            if(sendEmail.sendmail(updatedDTO.getUser().getEmail(), "Order Fulfilled", updatedDTO.toString(), false)){
                return new ResponseEntity<OrderDTO>(updatedDTO,HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

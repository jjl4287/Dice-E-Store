package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.persistence.GenericDAO;
import com.estore.api.estoreapi.persistence.OrderFileDAO;
import com.estore.util.sendEmail;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.OrderDTO;
import com.estore.api.estoreapi.model.User;

import java.util.*;

@RestController
@RequestMapping("orders")
public class OrderController {
    private static final Logger LOG = Logger.getLogger(StoreController.class.getName());
    private GenericDAO<Order,UUID> orderDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param orderDAO The Order Data Access Object to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public OrderController(GenericDAO<Order,UUID> orderDAO) {
        this.orderDAO = orderDAO;
    }

    /**
     * Responds to GET request for a order for the given id
     * 
     * @param id The id of the order
     * 
     * @return ResponseEntity with order object and HTTP status of OK if found
     * <br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable UUID id) {
        LOG.info("GET /orders/" + id);
        try {
            Order order = orderDAO.getbyID(id);
            if (order != null)
                return new ResponseEntity<OrderDTO>(new OrderDTO(order),HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all orders
     * 
     * @return ResponseEntity with array of order objects (may be empty) and
     * HTTP status of OK
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<OrderDTO[]> getOrders() {
        LOG.info("GET /orders");
        try {
            Order[] array = orderDAO.getall();
            OrderDTO[] dto = new OrderDTO[array.length];
            for (int i =0;i<array.length;i++) {
                dto[i]=new OrderDTO(array[i]);
            }
            return new ResponseEntity<OrderDTO[]>(dto, HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all orders whose name contains
     * the text in name
     * 
     * @param user The user parameter which contains the user used to find the orders
     * 
     * @return ResponseEntity with array of orders objects (may be empty) and
     * HTTP status of OK
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all orders that contain the text "nameHere"
     * GET http://localhost:8080/orders/?name=nameHere
     */
    @GetMapping("/")
    public ResponseEntity<OrderDTO[]> searchOrders(@RequestParam User user) {
        LOG.info("GET /orders/?name="+user);

        try {
            Order[] array = orderDAO.search(user);
            OrderDTO[] dto = new OrderDTO[array.length];
            for (int i =0;i<array.length;i++) {
                dto[i]=new OrderDTO(array[i]);
            }
            return new ResponseEntity<OrderDTO[]>(dto, HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a order with the provided order object
     * 
     * @param order - The orders to create
     * 
     * @return ResponseEntity with created orders object and HTTP status of CREATED
     * <br>
     * ResponseEntity with HTTP status of CONFLICT if orders object already exists
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO dto) {
        Order order = new Order(dto);
        LOG.info("POST /orders " + order);

        try {
            Order created = orderDAO.createNew(order);
            if(created != null) {
                return new ResponseEntity<OrderDTO>(new OrderDTO(created), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);        
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/fulfill")
    public ResponseEntity<OrderDTO> fulfillOrder(@RequestBody OrderDTO order) {
        LOG.info("POST /fulfillorders " + order);
        try {
            orderDAO.getbyID(order.getUuid()).fulfillOrder();
            if(sendEmail.sendmail(order.getUser().getEmail(), "Order Fulfilled", order.toString(), false)){
                return new ResponseEntity<OrderDTO>(order,HttpStatus.OK);
            }
                
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);        
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // /**
    //  * Updates the order with the provided order object, if it exists
    //  * 
    //  * @param order The orders to update
    //  * 
    //  * @return ResponseEntity with updated orders object and
    //  * HTTP status of OK if updated
    //  * <br>
    //  * ResponseEntity with HTTP status of NOT_FOUND if not found
    //  * <br>
    //  * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */
    // @PutMapping("")
    // public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
    //     LOG.info("PUT /orders " + order);

    //     try {
    //         Order updated = orderDAO.updateValue(order);
    //         if(updated != null) {
    //             return new ResponseEntity<Order>(updated, HttpStatus.OK);
    //         }
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    //     catch(IOException e){
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // /**
    //  * Deletes a order with the given id
    //  * 
    //  * @param id The id of the orders to delete
    //  * 
    //  * @return ResponseEntity HTTP status of OK if deleted
    //  * <br>
    //  * ResponseEntity with HTTP status of NOT_FOUND if not found
    //  * <br>
    //  * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Order> deleteOrder(@PathVariable UUID id) {
    //     LOG.info("DELETE /orders/" + id);

    //     try {
    //         boolean del = orderDAO.deleteValue(id);
    //         if(del) {
    //             return new ResponseEntity<Order>(HttpStatus.OK);
    //         }
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    //     catch(IOException e){
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}

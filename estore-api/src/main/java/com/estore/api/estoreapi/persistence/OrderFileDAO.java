package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.User;

import java.util.*;

/**
 * Provides for JSON File based persistence
 * 
 * {@literal @} Creates single instance of class and injects into others
 * as needed.
 * 
 * @author Team A - Bovines - Maximo Bustillo
 */

@Component
public class OrderFileDAO implements OrderDAO {
    private static final Logger LOG = Logger.getLogger(OrderFileDAO.class.getName());
    Map<UUID,Order> Orders;      // Provides a local copy of  Orders so that
                                        // we don't need to read from the file each time
    private ObjectMapper objectMapper;  // Provides conversion between Product
                                        // objects and JSON text format written
                                        // to the file
    private String filename;            // Filename to read from and write to

    /**
     * Creates an Order File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON object
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public OrderFileDAO(@Value("${orders.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the Orders from the file
    }

    /**
     * Generates an array of Orders from the tree map
     * 
     * @return  The array of Orders, may be empty
     */
    private Order[] getOrderArray() {
        return getOrderArray(null);
    }

    /**
     * Generates an array of Orders from the tree map for any
     * Orders that belong to the specified user
     * 
     * @param user the user to match against 
     * <br>
     * If user is null, the array contains all of the  Orders
     * in the tree map
     * <br> 
     * if user is null there is no filter
     * 
     * @return  The array of  Orders, may be empty
     */
    private Order[] getOrderArray(User user) {
        ArrayList<Order> OrderArrayList = new ArrayList<>();
        //iterate over array comparing users 
        for (Order order : Orders.values()) {
            if (user == null || order.getUser().equals(user)) {
                OrderArrayList.add(order);
            }
        }
        if(OrderArrayList.size()==0){
            return null;
        }
        Order[] OrderArray = new Order[OrderArrayList.size()];
        OrderArrayList.toArray(OrderArray);
        return OrderArray;
    }

    /**
     * Saves the Orders from the map into the file as an array of JSON objects
     * 
     * @return true if the Orders were written with little to no error.
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        LOG.info("Saving to file: "+filename);
        Order[] OrderArray = getOrderArray();

        // Translates the Java Objects to JSON objects, then to the file
        // writeValue will throw IOException if error
        // with file or reading from file
        objectMapper.writeValue(new File(filename), OrderArray);
        return true;
    }

    /**
     * Loads Orders from the JSON file into the map
     * <br>
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        LOG.info("Loading file: "+filename);
        Orders = new TreeMap<>();

        // Reads JSON objects from file into array of  Orders
        // readValue will throw IOException on error
        Order[] OrderArray = objectMapper.readValue(new File(filename), Order[].class);
        for (Order order : OrderArray) {
            Orders.put(order.getUuid(), order);
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order[] getallOrders() {
        synchronized(Orders) {
            return getOrderArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order[] findOrders(User user) {
        synchronized(Orders) {
            return getOrderArray(user);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order getOrderbyID(UUID id) {
        synchronized(Orders) {
            if (Orders.containsKey(id))
                return Orders.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order createNewOrder(Order order) throws IOException {
        synchronized(Orders) {
            // We create a new Order object because the id field is immutable
            // and we need to assign the next unique id
            Order newOrder = new Order(order.getProducts(), order.getUser(), order.getUuid(), order.getFulfilled());
            Orders.put(newOrder.getUuid(), newOrder);
            save(); // may throw an IOException
            return newOrder;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order updateOrder(Order order) throws IOException {
        synchronized(Orders) {
            if (Orders.containsKey(order.getUuid()) == false)
                return null;  // Order does not exist

            order.fulfillOrder();
            Orders.put(order.getUuid(), order);
            save(); // may throw an IOException
            return order;
        }
    }
}

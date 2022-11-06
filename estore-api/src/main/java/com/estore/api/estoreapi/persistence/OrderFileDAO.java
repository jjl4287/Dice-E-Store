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
 *              as needed.
 * 
 * @author Team A - Bovines
 */

@Component
public class OrderFileDAO implements GenericDAO<Order,UUID> {
    private static final Logger LOG = Logger.getLogger(OrderFileDAO.class.getName());
    Map<UUID,Order> Orders;      // Provides a local copy of products so that
                                        // we don't need to read from the file each time
    private ObjectMapper objectMapper;  // Provides conversion between Product
                                        // objects and JSON text format written
                                        // to the file
    private String filename;            // Filename to read from and write to

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON object
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public OrderFileDAO(@Value("${order.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the products from the file
    }

    /**
     * Generates an array of Products from the tree map
     * 
     * @return  The array of Products, may be empty
     */
    private Order[] getOrderArray() {
        return getOrderArray(null);
    }

    /**
     * Generates an array of Products from the tree map for any
     * Products that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the Products
     * in the tree map
     * <br> 
     * if containsText is null there is no filter
     * 
     * @return  The array of Products, may be empty
     */
    private Order[] getOrderArray(Object user) {
        ArrayList<Order> OrderArrayList = new ArrayList<>();

        for (Order order : Orders.values()) {
            if (user == null || order.getUser().equals(user)) {
                OrderArrayList.add(order);
            }
        }

        Order[] OrderArray = new Order[OrderArrayList.size()];
        OrderArrayList.toArray(OrderArray);
        return OrderArray;
    }

    /**
     * Saves the Products from the map into the file as an array of JSON objects
     * 
     * @return true if the Products were written with little to no error.
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Order[] OrderArray = getOrderArray();

        // Translates the Java Objects to JSON objects, then to the file
        // writeValue will throw IOException if error
        // with file or reading from file
        objectMapper.writeValue(new File(filename),OrderArray);
        return true;
    }

    /**
     * Loads Products from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        Orders = new TreeMap<>();

        // Reads JSON objects from file into array of Products
        // readValue will throw IOException on error
        Order[] OrderArray = objectMapper.readValue(new File(filename),Order[].class);
        for (Order order: OrderArray){
            Orders.put(order.getUuid(), order);
        }

        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Order[] getall() {
        synchronized(Orders) {
            return getOrderArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Order[] search(Object deliniator) {
        if(deliniator instanceof User){
            synchronized(Orders) {
                return getOrderArray(deliniator);
            }
        }
        else{
            return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Order getbyID(UUID id) {
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
    public Order createNew(Order order) throws IOException {
        synchronized(Orders) {
            // We create a new Product object because the id field is immutable
            // and we need to assign the next unique id
            Order newOrder = new Order(order.getProducts(), order.getUser(), UUID.randomUUID());
            Orders.put(newOrder.getUuid(),newOrder);
            save(); // may throw an IOException
            return newOrder;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Order updateValue(Order order) throws IOException {
        synchronized(Orders) {
            if (Orders.containsKey(order.getUuid()) == false)
                return null;  // Product does not exist

            Orders.put(order.getUuid(),order);
            save(); // may throw an IOException
            return Orders.get(order.getUuid());
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteValue(UUID id) throws IOException {
        synchronized(Orders) {
            if (Orders.containsKey(id)) {
                Orders.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}

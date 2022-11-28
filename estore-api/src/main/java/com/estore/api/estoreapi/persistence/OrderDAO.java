package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.Order;
import java.util.*;

/**
 * Defines the interface for Order object persistence
 * 
 * @author Team A - Bovines - Maximo Bustillo
 */
public interface OrderDAO{
    /**
     * Retrieves all Orders
     * 
     * @return An array of Order objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Order[] getallOrders() throws IOException;

    /**
     * Finds all Orders whose belone to a given User
     * 
     * @param User The User to match against
     * 
     * @return An array of Orders whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Order[] findOrders(User User) throws IOException;

    /**
     * Retrieves an Order with the given id
     * 
     * @param id The id of Order to get
     * 
     * @return an Order object with the matching id
     * <br>
     * null if no Order with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
   Order getOrderbyID(UUID id) throws IOException;


    /**
     * Creates and saves an order
     * 
     * @param Order Order to be created and saved
     * <br>
     * The id of the Order object is ignored and a new unique id is assigned
     *
     * @return new Order if successful, on error false
     * 
     * @throws IOException if an issue with underlying storage
     */
   Order createNewOrder(Order Order) throws IOException;

    /**
     * Updates and saves an Order
     * 
     * @param Order object to be updated and saved
     * 
     * @return updated Order if successful, null if Order not found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
   Order updateOrder(Order order) throws IOException;


}

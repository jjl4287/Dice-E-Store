package com.estore.api.estoreapi.Models;

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
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class OrderTests {
    @Test
    public void testCtor() {
        // Setup
        User expected_usr= new user();
        Set<Product> expected_purchase = new HashSet<>();
        boolean expected_fulfillment=false;
        for(int i =0;i<5;i++){
            expected_purchase.add(new Product(i, "p"+i, 25-i, 25.99f));
        }




        // Invoke
        Order order  = new Order(expected_purchase,expected_usr);

        // Analyze
        assertEquals(expected_purchase,order.getProducts());
        assertEquals(expected_usr,order.getUser());
        assertEquals(expected_fulfillment,order.getFulfillment());
        assertNotNull(order.getUuid());
    }

    @Test
    public void testFullfillment() {
        // Setup
        User usr= new user();
        Set<Product> purchase = new HashSet<>();
        for(int i =0;i<5;i++){
            purchase.add(new Product(i, "p"+i, 25-i, 25.99f));
        }

        Order order  = new Order(purchase,usr);

        boolean expected_fulfillment=true;

        //invoke 
        order.fulfillOrder();
        
        // Analyze
        assertEquals(expected_fulfillment,order.getFulfillment());
    }


    @Test
    public void testEquality() {
     // Setup
     User usr= new user();
     Set<Product> purchase = new HashSet<>();
     for(int i =0;i<5;i++){
         purchase.add(new Product(i, "p"+i, 25-i, 25.99f));
     }

    Order order  = new Order(purchase,usr);
    Order order2 = new Order(purchase, usr);
    
    // Analyze
    assertEquals(order,order);
    assertNotEquals(order,order2);
    }


    @Test
    public void testToString() {
        // Setup
        User usr= new user();
        Set<Product> purchase = new HashSet<>();
        for(int i =0;i<5;i++){
            purchase.add(new Product(i, "p"+i, 25-i, 25.99f));
        }
        String expected_string = String.format(Order.STRING_FORMAT,purchase,usr);

        Order order = new Order(purchase,usr);

        // Invoke
        String actual_string = order.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

    
}
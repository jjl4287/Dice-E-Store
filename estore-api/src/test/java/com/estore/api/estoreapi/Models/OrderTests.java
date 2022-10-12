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
        int id = 1;
        String userName="test";
        String password= "test";
        User expected_usr= new User(id,userName,password);
        Set<Product> expected_purchase = new HashSet<>();
        UUID expected_uuid= UUID.randomUUID();
        boolean expected_fulfillment=false;
        for(int i =0;i<5;i++){
            expected_purchase.add(new Product(i, "p"+i, 25-i, 25.99f));
        }




        // Invoke
        Order order1  = new Order(expected_purchase,expected_usr);
        Order order2  = new Order(expected_purchase,expected_usr,expected_uuid);

        // Analyze
        assertEquals(expected_purchase,order1.getProducts());
        assertEquals(expected_usr,order1.getUser());
        assertEquals(expected_fulfillment,order1.getFulfillment());
        assertNotNull(order1.getUuid());

        assertEquals(expected_purchase,order2.getProducts());
        assertEquals(expected_usr,order2.getUser());
        assertEquals(expected_fulfillment,order2.getFulfillment());
        assertEquals(expected_uuid,order2.getUuid());
    }

    @Test
    public void testFullfillment() {
        // Setup
        int id = 1;
        String userName="test";
        String password= "test";
        User usr= new User(id,userName,password);
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
     int id = 1;
    String userName="test";
    String password= "test";
    User usr= new User(id,userName,password);
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
        int id = 1;
        String userName="test";
        String password= "test";
        User usr= new User(id,userName,password);
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
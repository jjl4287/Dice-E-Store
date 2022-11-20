package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.*;

/**
 * The unit test suite for the OrderDTO class
 * 
 * @author Team A - Bovines - Maximo Bustillo 
 */
@Tag("Model-tier")
public class OrderDTOTest {
    @Test
    public void testCtor() {
        // Setup
        int id = 1;
        String userName="test";
        String password= "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User expected_usr= new User(id,userName,password,shoppingCart,email);
        ArrayList<Product> expected_purchase = new ArrayList<>();
        UUID expected_uuid= UUID.randomUUID();
        boolean expected_fulfillment=false;
        for(int i =0;i<5;i++){
            expected_purchase.add(new Product(i, "p"+i, 25-i, 25.99f, "url", "desc"));
        }



        // Invoke
        OrderDTO order1  = new OrderDTO(expected_purchase,expected_usr);
        
        OrderDTO order2  = new OrderDTO(expected_purchase,expected_usr,expected_uuid,false,0,0);

        // Analyze
        assertEquals(expected_purchase,order1.getProducts());
        assertEquals(expected_usr,order1.getUser());
        assertEquals(expected_fulfillment,order1.getFulfilled());
        assertEquals(0,order1.getPrice());
        assertEquals(0,order1.getSize());
        assertNotNull(order1.getUuid());

        assertEquals(expected_purchase,order2.getProducts());
        assertEquals(expected_usr,order2.getUser());
        assertEquals(expected_fulfillment,order2.getFulfilled());
        assertEquals(0,order2.getPrice());
        assertEquals(0,order2.getSize());
        assertEquals(expected_uuid,order2.getUuid());
    }


    @Test
    public void testEquality() {
     // Setup
     int id = 1;
    String userName="test";
    String password= "test";
    Set<Product> shoppingCart = new HashSet();
    String email = "test";
    User usr= new User(id,userName,password,shoppingCart,email);
    ArrayList<Product> purchase = new ArrayList<>();
     for(int i =0;i<5;i++){
         purchase.add(new Product(i, "p"+i, 25-i, 25.99f, "url", "desc"));
     }

    //create
    OrderDTO order  = new OrderDTO(purchase,usr);
    OrderDTO order2 = new OrderDTO(purchase, usr);
    OrderDTO order3 = new OrderDTO(purchase, usr,order.getUuid(),false,0,0);
    Product p = new Product(1, "test", 1, 1, "url", "desc");
    
    // Analyze
    assertEquals(order,order);
    assertNotEquals(order,order2);
    assertEquals(order,order3);
    assertNotEquals(order, p);
    }


    @Test
    public void testToString() {
        // Setup
        int id = 1;
        String userName="test";
        String password= "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User usr= new User(id,userName,password,shoppingCart,email);
        ArrayList<Product> purchase = new ArrayList<>();
        for(int i =0;i<5;i++){
            purchase.add(new Product(i, "p"+i, 25-i, 25.99f, "url", "desc"));
        }
        UUID uuid = UUID.randomUUID();
        boolean fulfill = false;
        int size = 0;
        double price = 0;
        String expected_string = String.format(OrderDTO.STRING_FORMAT,purchase.toString(),usr,uuid,fulfill,size,price);

        OrderDTO order = new OrderDTO( purchase,usr,uuid,fulfill,size,price);

        // Invoke
        String actual_string = order.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
    @Test
    public void testHashCode() {
        // Setup
        int id = 1;
        String userName="test";
        String password= "test";
        Set<Product> shoppingCart = new HashSet();
        String email = "test";
        User usr= new User(id,userName,password,shoppingCart,email);
        ArrayList<Product> purchase = new ArrayList<>();
        for(int i =0;i<5;i++){
            purchase.add(new Product(i, "p"+i, 25-i, 25.99f, "url", "desc"));
        }

        OrderDTO order = new OrderDTO( purchase,usr);


        // Analyze
        assertNotNull(order.hashCode());
    }

    
}
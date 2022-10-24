package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Product;

/**
 * The unit test suite for the Product class
 * 
 * @author Team A Bovines - Chris Ferioli, Maximo Bustillo, Vincent Sarubbi
 */
@Tag("Model-tier")
public class ProductTests {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Mr. Dice";
        int expected_qty = 10;
        float expected_price = 39.99f;



        // Invoke
        Product product  = new Product(expected_id,expected_name,expected_qty,expected_price);

        // Analyze
        assertEquals(expected_id,product.getId());
        assertEquals(expected_name,product.getName());
        assertEquals(expected_qty,product.getQty());
        assertEquals(expected_price,product.getPrice());
    }

    @Test
    public void testName() {
        // Setup
        int expected_id = 99;
        String expected_name = "Mr. Dice";
        int expected_qty = 10;
        float expected_price = 39.99f;
        String newName = "Ms. Dice";



        // Create
        Product product  = new Product(expected_id,expected_name,expected_qty,expected_price);

        //invoke 
        product.setName(newName);
        
        // Analyze
        assertEquals(newName,product.getName());
    }


    @Test
    public void testQty() {
     // Setup
        int expected_id = 99;
        String expected_name = "Mr. Dice";
        int expected_qty = 10;
        float expected_price = 39.99f;
        int newQty = 5;



        // Create
        Product product  = new Product(expected_id,expected_name,expected_qty,expected_price);
        
        //invoke 
        product.setQty(newQty);
        
        // Analyze
        assertEquals(newQty,product.getQty());
    }

    @Test
    public void testPrice() {
        // Setup
        int expected_id = 99;
        String expected_name = "Mr. Dice";
        int expected_qty = 10;
        float expected_price = 39.99f;
        float newPrice = 3.99f;



        // Create
        Product product  = new Product(expected_id,expected_name,expected_qty,expected_price);
        
        //invoke 
        product.setPrice(newPrice);
        
        // Analyze
        assertEquals(newPrice,product.getPrice());
    }

    @Test
    public void testequals() {
        // Setup
        int expected_id = 99;
        String expected_name = "Mr. Dice";
        int expected_qty = 10;
        float expected_price = 39.99f;



        // Create
        Product product  = new Product(expected_id,expected_name,expected_qty,expected_price);
        Product product2  = new Product(expected_id,expected_name,expected_qty-1,expected_price-1);
        Product product3  = new Product(expected_id-1,expected_name,expected_qty-1,expected_price-1);
        Product product4  = new Product(expected_id-1,expected_name+"1",expected_qty,expected_price);
        Object o = new Object();
        //invoke 
        
        // Analyze
        assertEquals(product,product2);
        assertNotEquals(product2,product3);
        assertNotEquals(product3,product4);
        assertNotEquals(product,o);
    }



    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Mr. Dice";
        int qty = 10;
        float price = 39.99f;
        String expected_string = String.format(Product.STRING_FORMAT,id,name, qty, price);
        Product product = new Product(id, name, qty, price);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

    
}
package com.estore.api.estoreapi.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.util.sendEmail;


/**
 * The unit test suite for the sendEmail class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class sendEmailTest {
    @Test
    public void testCtor() {
        // Setup
        String expected_sender= "swen261.bovine@gmail.com";
        String expected_subject = "Your sendEmail Has been shipped";
        String password = "nyijfcnuywyjoalz";
        boolean debug = true;



        // Invoke
        sendEmail mail  = new sendEmail(expected_sender,expected_subject,password,debug);

        // Analyze
        assertEquals(expected_sender,mail.getSender());
        assertEquals(expected_subject,mail.getSubject());
    }

    // @Test
    // public void testOrderToBody() {
    //    
    // }


    @Test
    public void testSendMail() {
    // Setup
    User usr= new user();
    Set<Product> purchase = new HashSet<>();
    for(int i =0;i<5;i++){
        purchase.add(new Product(i, "p"+i, 25-i, 25.99f));
    }
    String sender= "swen261.bovine@gmail.com";
    String subject = "Your sendEmail Has been shipped";
    String password = "nyijfcnuywyjoalz";
    String recipient = "maximokbustillo@gmail.com";
    boolean debug = false;
    Order order = new Order(purchase,usr);
    

    sendEmail mail  = new sendEmail(sender,subject,password,debug);
    
    // Analyze
    assertTrue(mail.sendMail(recipient, "Your order has been shipped.\n Order: "+order.toString()));
    }


    
}
package com.estore.util;

// Java program to send email

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

// import com.estore.api.estoreapi.model.*;

// import com.estore.api.estoreapi.model.Product;

import javax.mail.Session;
import javax.mail.Transport;


public class sendEmail {
    private String sender;
    private String subject;
    private Session session;

    public sendEmail(String sender,String password,String subject,boolean debug) { 
        // Sender's email ID needs to be mentioned
        this.sender=sender;
        this.subject=subject;
        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        this.session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(sender, password);

            }

        });
             // Used to debug SMTP issues
        session.setDebug(debug);
    }
    
    public String getSender() {
        return sender;
    }
    public String getSubject() {
        return subject;
    }
	// public String orderToBody(Order order){
    //     StringBuilder b = new StringBuilder();
    //     b.append("Your order has been shipped.\n")
    //     b.append("Your order");
    //     double total = 0;
    //     for(Product p: order.getProducts()){
    //         b.append(String.format("%d %s (%f)\n",p.getQty(),p.getName(),p.getPrice()));
    //         total+=p.getQty()*p.getPrice();
    //     }
    //     b.append(String.format("For a total of %f.", total));
    //     return b.toString();
    // }

    public boolean sendMail(String recipient,String body){
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
    
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender));
    
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
    
            // Set Subject: header field
            message.setSubject(subject);
    
            // Now set the actual message
            message.setText(body);
    
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

    }
}


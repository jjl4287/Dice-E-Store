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
    private final static String sender = "swen261.bovine@gmail.com";
    private final static String password = "nenqdckafwcedcdr";

    public static boolean sendmail(String recipient, String subject, String body, boolean debug) {
        // Sender's email ID needs to be mentioned
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
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(sender, password);

            }

        });
        // Used to debug SMTP issues
        session.setDebug(debug);

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

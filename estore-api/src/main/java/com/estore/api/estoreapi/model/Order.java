package com.estore.api.estoreapi.model;
import java.util.*;

import javax.swing.plaf.FontUIResource;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Order
 *
 * @author Team A - Bovines - Maximo Bustillo
 */

public class Order {
    public static final String STRING_FORMAT = "Product [products=%s, user=%s, uuid=%s]";

    @JsonProperty("products") private Set<Product> products;
    @JsonProperty("user") private User user;
    @JsonProperty("UUID") private UUID uuid;
    private boolean fulfilled=false;

    /**
     * Create a new User with the given id, username, and password
     * 
     * @param products a set of products included in the order
     * @param user The associated user 
     *
     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If field
     * is not provided it will be set to default java value
     */
    public Order(Set<Product> purchase,User user){
        this.products= new HashSet<>(purchase);
        this.uuid = UUID.randomUUID();
        this.user=user;
    }
    /**
     * Create a new User with the given id, username, and password
     * @param products a set of products included in the order
     * @param user The associated user
     * @param uuiid a unique identifier of the order
     * 
     *
     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If field
     * is not provided it will be set to default java value
     */
    public Order(@JsonProperty("products") Set<Product> purchase, @JsonProperty("user") User user,@JsonProperty("UUID") UUID uuid){
        this.products= new HashSet<>(purchase);
        this.uuid = uuid;
        this.user=user;
    }
    /**
     * Getter for products
     * @return products of order
     */
    public Set<Product> getProducts(){
        Set<Product> copy = new HashSet<>();
        for(Product p : products){
            copy.add(p);
        }
        return copy;
    }
    /**
     * Getter for user
     * @return user of order
     */
    public User getUser(){
        return new User(user.getId(),user.getUserName(),user.getPassword());
    }
    /**
     * Getter for uuid
     * @return uuid of order
     */
    public UUID getUuid() {
        return uuid;
    }
    
    /**
     * mutator for fulfuillment
     * changes it to true
     */
    public void fulfillOrder(){
        //this.user.updateOrder(this);
        fulfilled=true;
    }
    /**
     * Getter for fulfuillment
     * @return fulfillment status of order
     */
    public boolean getFulfillment(){
        return fulfilled;
    }

    @Override
    /**
     * an equals function override for user comparison
     * @param o to be compared
     * @return true if equal, false if not.
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return this.uuid.equals(order.uuid);
        
    }

    /**
     * a hashcode generator for products
     * @return hashcode for product
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }    

    @Override
    /**
     * to string function
     */
    public String toString() {
        return String.format(STRING_FORMAT, products, user,uuid);
    }

}

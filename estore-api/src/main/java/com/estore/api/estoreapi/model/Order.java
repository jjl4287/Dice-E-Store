package com.estore.api.estoreapi.model;
import java.util.*;

import javax.swing.plaf.FontUIResource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents an Order
 *
 * @author Team A - Bovines - Maximo Bustillo
 */

public class Order {
    public static final String STRING_FORMAT = "Product [products=%s, user=%s, uuid=%s]";

    private Set<Product> products;
    private User user;
    private UUID uuid;
    private boolean fulfilled=false;


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
    @JsonCreator
    public Order(@JsonProperty("products") Set<Product> products, @JsonProperty("user") User user, @JsonProperty("UUID") UUID uuid, @JsonProperty("fulfilled") boolean fulfilled){
        this.products= new HashSet<>(products);
        this.uuid=uuid;
        this.user=user;
        this.fulfilled=fulfilled;
    }

    /**
     * Create a new User with the given id, username, and password
     * 
     * @param products a set of products included in the order
     * @param user The associated user 
     *
     *
     * 
     */
    public Order(Set<Product> products,User user){
        this.products= new HashSet<>(products);
        this.uuid = UUID.randomUUID();
        this.user=user;
    }
    /**
     * Create a new User with the given id, username, and password
     * 
     * @param orderDTO an orderDTO
     *
     *
     * 
     */
    public Order(OrderDTO dto){
        this.products= new HashSet<>(dto.getProducts());
        this.uuid = dto.getUuid()==null?UUID.randomUUID():dto.getUuid();
        this.user=dto.getUser();
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
        return new User(user);
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
        fulfilled=true;
    }
    /**
     * Getter for fulfuillment
     * @return fulfillment status of order
     */
    public boolean getFulfilled(){
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

package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents an Order that can be sent to the front end
 *
 * @author Team A - Bovines - Maximo Bustillo
 */

public class OrderDTO {
    private ArrayList<Product> products;
    private User user;
    private UUID uuid;
    private boolean fulfilled = false;
    private int size;
    private double price;
    public static final String STRING_FORMAT = "Product [products=%s, user=%s, uuid=%s, fulfilled=%b, size=%d, price=%f]";


    /**
     * Create a new OrderDTO with the given product list, user, and UUID
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
    public OrderDTO(@JsonProperty("products") ArrayList<Product> products, @JsonProperty("user") User user, @JsonProperty("UUID") UUID uuid, 
        @JsonProperty("fulfilled") boolean fulfilled, @JsonProperty("size") int size, @JsonProperty("price") double price){
        this.products = new ArrayList<Product>(products);
        this.uuid=uuid;
        this.user=user;
        this.fulfilled = fulfilled;
        this.size=size;
        this.price=price;
    }
    /**
     * Create a new OrderDTO with the given products, and User
     * @param products a set of products included in the order
     * @param user The associated user
     *
     */
    public OrderDTO(ArrayList<Product> products,User user){
        this.products = new ArrayList<Product>(products);
        this.uuid=UUID.randomUUID();
        this.user=user;
    }
    
     /**
     * Create a new OrderDTO given an order
     * @param order an order to package as a dto 
     *
     */
    public OrderDTO(Order order){
        this.products = new ArrayList<Product>(order.getProducts());
        this.uuid=order.getUuid();
        this.user=order.getUser();
        this.fulfilled = order.getFulfilled();

        this.size = this.products.size();
        this.price = 0; 
        for(Product p:this.products){
            this.price +=p.getQty()*p.getPrice();
        }
    }

    /**
     * Getter for products
     * @return products of order
     */
    public ArrayList<Product> getProducts() {
        return products;
    }
    /**
     * Getter for uuid
     * @return uuid of order
     */
    public UUID getUuid() {
        return uuid;
    }
    /**
     * Getter for user
     * @return user of order
     */
    public User getUser() {
        return user;
    }
    /**
     * Getter for fulfuillment
     * @return fulfillment status of order
     */
    public boolean getFulfilled() {
        return fulfilled;
    }
    /**
     * Getter for size
     * @return size of order
     */
    public int getSize() {
        return size;
    }
    /**
     * Getter for price
     * @return price of order
     */
    public double getPrice() {
        return price;
    }

    /**
     * an equals function override for user comparison
     * @param o to be compared
     * @return true if equal, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        //orders are only equal if they have teh same UUID
        if(obj instanceof OrderDTO){
            OrderDTO order = (OrderDTO)obj;
            return this.uuid.equals(order.uuid);
        }
        return false;
    }

    /**
     * to string function
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, products, user,uuid,fulfilled,size,price);
    }
}

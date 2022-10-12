package com.estore.api.estoreapi.model;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

    public static final String STRING_FORMAT = "Product [products=%s, user=%s, uuid=%s]";

    @JsonProperty("products") private Set<Product> products;
    @JsonProperty("user") private User user;
    @JsonProperty("UUID") private UUID uuid;
    private boolean fulfilled=false;

    public Order(@JsonProperty("products") Set<Product> purchase, @JsonProperty("user") User user){
        this.products= new HashSet<>(purchase);
        this.uuid = UUID.randomUUID();
        this.user=user;
    }
    public Order(@JsonProperty("products") Set<Product> purchase, @JsonProperty("user") User user,@JsonProperty("UUID") UUID uuid){
        this.products= new HashSet<>(purchase);
        this.uuid = uuid;
        this.user=user;
    }

    public Set<Product> getProducts(){
        Set<Product> copy = new HashSet<>();
        for(Product p : products){
            copy.add(p);
        }
        return copy;
    }
    public User getUser(){
        return new User(user.getId(),user.getUserName(),user.getPassword());
    }
    public UUID getUuid() {
        return uuid;
    }

    public void fulfillOrder(){
        fulfilled=true;
    }

    public boolean getFulfillment(){
        return fulfilled;
    }

    @Override
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
    public String toString() {
        return String.format(STRING_FORMAT, products, user,uuid);
    }

}

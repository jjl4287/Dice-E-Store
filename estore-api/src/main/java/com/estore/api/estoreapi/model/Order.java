package com.estore.api.estoreapi.model;
import java.util.*;

public class Order {

    public static final String STRING_FORMAT = "Product [products=%s, user=%s]";

    private Set<Product> products;
    private User user;
    private UUID uuid;
    private boolean fulfilled=false;

    public Order(Set<Product> purchase,User user){
        this.products= new HashSet<>(purchase);
        this.uuid = UUID.randomUUID();
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
        return user.Clone();
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

}

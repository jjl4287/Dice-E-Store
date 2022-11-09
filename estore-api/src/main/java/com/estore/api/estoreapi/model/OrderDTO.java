package com.estore.api.estoreapi.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class OrderDTO {
    private ArrayList<Product> products;
    private User user;
    private UUID uuid;
    private boolean fulfilled = false;
    private int size;
    private double price;
    public static final String STRING_FORMAT = "Product [products=%s, user=%s, uuid=%s, fulfilled=%b, size=%d, price=%f]";

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
    public OrderDTO(ArrayList<Product> products,User user){
        this.products = new ArrayList<Product>(products);
        this.uuid=UUID.randomUUID();
        this.user=user;
    }
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
    public void fulfillOrder() {
        this.fulfilled = true;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public UUID getUuid() {
        return uuid;
    }
    public User getUser() {
        return user;
    }
    public boolean getFulfilled() {
        return fulfilled;
    }
    public int getSize() {
        return size;
    }
    public double getPrice() {
        return price;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof OrderDTO){
            OrderDTO order = (OrderDTO)obj;
            return this.uuid.equals(order.uuid);
        }
        return false;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format(STRING_FORMAT, products, user,uuid,fulfilled,size,price);
    }
}

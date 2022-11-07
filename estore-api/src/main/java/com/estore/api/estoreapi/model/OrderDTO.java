package com.estore.api.estoreapi.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class OrderDTO {
    private ArrayList<Product> purchase;
    private User user;
    private UUID uuid;

    @JsonCreator
    public OrderDTO(@JsonProperty("products") ArrayList<Product> purchase, @JsonProperty("user") User user, @JsonProperty("UUID") UUID uuid){
        this.purchase = new ArrayList<Product>(purchase);
        this.uuid=uuid;
        this.user=user;
    }
    public OrderDTO(ArrayList<Product> purchase,User user){
        this.purchase = new ArrayList<Product>(purchase);
        this.uuid=UUID.randomUUID();
        this.user=user;
    }
    public ArrayList<Product> getPurchase() {
        return purchase;
    }
    public UUID getUuid() {
        return uuid;
    }
    public User getUser() {
        return user;
    }
}

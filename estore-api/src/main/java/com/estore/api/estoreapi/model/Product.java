package com.estore.api.estoreapi.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product
 * 
 * @author Team A - Bovines
 */
public class Product {

    // Package private for tests
    public static final String STRING_FORMAT = "Product [id=%d, name=%s, qty=%d, price=%f, url=%s, description=%s]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private float price;
    @JsonProperty("qty")
    private int qty;
    @JsonProperty("url")
    private String url;
    @JsonProperty("description")
    private String description;

    /**
     * Create a product with the given id and name
     * 
     * @param id          The id of the product
     * @param name        The name of the product
     * @param qty         the qty of products
     * @param price       the price of product
     * @param url         the image url of the product
     * @param description the description of the product
     * 
     * 
     *                    {@literal @}JsonProperty is used in serialization and
     *                    deserialization
     *                    of the JSON object to the Java object in mapping the
     *                    fields. If field
     *                    is not provided it will be set to default java value
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name,
            @JsonProperty("qty") int qty, @JsonProperty("price") float price, @JsonProperty("url") String url,
            @JsonProperty("description") String description) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.url = url;
        this.description = description;
    }

    /**
     * an equals function override for product comparison
     * 
     * @param product to be compared
     * @return true if equal, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name);
    }

    /**
     * a hashcode generator for products
     * 
     * @return hashcode for product
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /**
     * Getter for price
     * 
     * @return price of product
     */
    public float getPrice() {
        return price;
    }

    /**
     * Setter for price
     * 
     * @param price price of product
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Getter for qty
     * 
     * @return qty of products
     */
    public int getQty() {
        return qty;
    }

    /**
     * setter for quantity
     * 
     * @param qty quantity of products
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * getter for the product id
     * 
     * @return The id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * gets name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product - necessary for JSON/JAVA deserialization
     * 
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets image url of the product
     * 
     * @return The image url of the product
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the image url of the product
     * 
     * @param url The image url of the product
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * gets the descritpion of the product
     * 
     * @return The description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product
     * 
     * @param description The description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, qty, price, url, description);
    }
}
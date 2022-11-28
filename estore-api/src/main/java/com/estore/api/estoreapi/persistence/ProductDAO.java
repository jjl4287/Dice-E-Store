package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface for Product object persistence
 * 
 * @author Team A - Bovines
 */
public interface ProductDAO {
    /**
     * Retrieves all plain link products
     * 
     * @return An array of Product objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProducts() throws IOException;

    /**
     * Finds all plain link products whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of products whose nemes contains the given text, may be
     *         empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] searchProducts(String containsText) throws IOException;

    /**
     * Retrieves a product with the given id
     * 
     * @param id The id of the Product to get
     * 
     * @return a Product object with the matching id
     *         <br>
     *         null if no Product with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

    /**
     * Creates and saves a product
     * 
     * @param Product product object to be created and saved
     *                <br>
     *                The id of the Product object is ignored and a new unique id is
     *                assigned
     *
     * @return new Product if successful, on error false
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createProduct(Product Product) throws IOException;

    /**
     * Updates and saves a product
     * 
     * @param Product object to be updated and saved
     * 
     * @return updated Product if successful, null if
     *         Product not found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product Product) throws IOException;

    /**
     * Deletes a product with the given id
     * 
     * @param id The id of the {@link Product Product}
     * 
     * @return true if the Product was deleted
     *         <br>
     *         false if Product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;
}

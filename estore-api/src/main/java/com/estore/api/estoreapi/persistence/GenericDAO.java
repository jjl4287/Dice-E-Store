package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;
import java.util.*;

/**
 * Defines the interface for Product object persistence
 * 
 * @author Team A - Bovines
 */
public interface GenericDAO <T,V> {
    /**
     * Retrieves all plain link products
     * 
     * @return An array of T objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    T[] getall() throws IOException;

    /**
     * Finds all plain link products whose name contains the given text
     * 
     * @param deliniator The object to match against
     * 
     * @return An array of T whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    T[] search(Object deliniator) throws IOException;

    /**
     * Retrieves a product with the given id
     * 
     * @param id The id of  T to get
     * 
     * @return a T object with the matching id
     * <br>
     * null if no T with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    T getbyID(V id) throws IOException;


    /**
     * Creates and saves a product
     * 
     * @param value T object to be created and saved
     * <br>
     * The id of the T object is ignored and a new unique id is assigned
     *
     * @return new Product if successful, on error false
     * 
     * @throws IOException if an issue with underlying storage
     */
    T createNew(T Value) throws IOException;

    /**
     * Updates and saves a product
     * 
     * @param Value object to be updated and saved
     * 
     * @return updated T if successful, null if
     * T not found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    T updateValue(T Value) throws IOException;

    /**
     * Deletes a product with the given id
     * 
     * @param id The id of the {@link T Product}
     * 
     * @return true if the T was deleted
     * <br>
     * false if T with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteValue(V id) throws IOException;

}

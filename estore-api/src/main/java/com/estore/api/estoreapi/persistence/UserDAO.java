package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.Set;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the interface for User object persistence
 *
 * @author Team A - Bovines - Brady Self
 */
public interface UserDAO {
    /**
     * Retrieves all plain link users
     *
     * @return An array of user objects, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves guest account
     * 
     * @return guest account
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getGuest() throws IOException;

    /**
     * Finds all plain link user whose username contains the given text
     *
     * @param containsText The text to match against
     *
     * @return An array of users whose usernames contains the given text, may be empty
     *
     * @throws IOException if an issue with underlying storage
     */
    User[] searchUsers(String containsText) throws IOException;

    /**
     * Retrieves a user with the given id
     *
     * @param id The id of the User to get
     *
     * @return a User object with the matching id
     * <br>
     * null if no Product with a matching id is found
     *
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;

    /**
     * Retrieves the current user
     *
     * @return the current user (could be guest)
     *
     * @throws IOException if an issue with underlying storage
     */
    User getCurrentUser() throws IOException;

    /**
     * Creates and saves a User
     *
     * @param User User object to be created and saved
     * <br>
     * The id of the User object is ignored and a new unique id is assigned
     *
     * @return new User if successful, on error false
     *
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User User) throws IOException;

    /**
     * Updates and saves a User
     *
     * @param User user to be updated and saved
     *
     * @return updated User if successful, null if
     * User not found
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * Deletes a user with the given id
     *
     * @param id The id of the {@link User User}
     *
     * @return true if the User was deleted
     * <br>
     * false if User with the given id does not exist
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;

    /**
     * logs in a user
     * 
     * @param username the username of the user
     * @param password the password of the user
     * 
     * @return the user if log in was successful and null otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User login(String username, String password) throws IOException;

    /**
     * logs out a user
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    void logout() throws IOException;

    /**
     * Retrieves the current users shopping cart
     *
     * @return the current users shopping cart (could be guest)
     *
     * @throws IOException if an issue with underlying storage
     */
    Set<Product> getShoppingCart() throws IOException;

    /**
     * Adds the product to the users shopping cart
     * 
     * @throws IOException
     */
    void addToCart(Product product) throws IOException;
}

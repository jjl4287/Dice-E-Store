package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

/**
 * Provides for JSON File based persistence
 * 
 * {@literal @} Creates single instance of class and injects into others
 *              as needed.
 * 
 * @author Team A - Bovines - Brady Self
 */

@Component
public class UserFileDAO implements UserDAO {
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<Integer,User> users;            // Provides a local copy of users so that
                                        // we don't need to read from the file each time
    private ObjectMapper objectMapper;  // Provides conversion between User
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;          // The next Id to assign to a new User
    private String filename;            // Filename to read from and write to
    private User currentUser;           // The user currently logged in
    private final User GUEST_USER = new User(0, "Guest", "guestPassword", new HashSet<Product>(), "guest@guest.com");

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON object
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.currentUser = GUEST_USER;
        load();  // load the users from the file
    }

    /**
     * Generates the next id 
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of Users from the tree map
     * 
     * @return  The array of Users, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of Users from the tree map for any
     * Users that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the Users
     * in the tree map
     * <br> 
     * if containsText is null there is no filter
     * 
     * @return  The array of Users, may be empty
     */
    private User[] getUsersArray(String containsText) {
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getUsername().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    /**
     * Saves the Users from the map into the file as an array of JSON objects
     * 
     * @return true if the Users were written with little to no error.
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Translates the Java Objects to JSON objects, then to the file
        // writeValue will throw IOException if error
        // with file or reading from file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    /**
     * Loads Users from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        // Reads JSON objects from file into array of Users
        // readValue will throw IOException on error
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each User to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        // Make the next id one greater than maximum from file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] searchUsers(String containsText) {
        synchronized(users) {
            return getUsersArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User getUser(int id) {
        synchronized(users) {
            if (users.containsKey(id))
                return users.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(users) {
            // We create a new User object because the id field is immutable
            // and we need to assign the next unique id
            User newUser = new User(user);
            users.put(newUser.getId(),newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized(users) {
            if (users.containsKey(user.getId()) == false)
                return null;  // User does not exist

            users.put(user.getId(),user);
            save(); // may throw an IOException
            return user;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            }
            else
                return false;
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public User login( String username, String password) throws IOException{
        User[] userArray = getUsersArray(username);
        for (User user : userArray) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    this.currentUser = user;
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() {
        this.currentUser = GUEST_USER;
    }

    /**
     * {@inheritDoc}
     */
    public User getCurrentUser() throws IOException {
        return this.currentUser;
    }

    /**
     * {@inheritDoc}
     */
    public User getGuest() throws IOException {
        return GUEST_USER;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Product> getShoppingCart() {
        return this.currentUser.getShoppingCart();
    }
}


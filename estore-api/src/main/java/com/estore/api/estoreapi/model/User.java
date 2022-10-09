package com.estore.api.estoreapi.model;

import java.util.Objects;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a User
 *
 * @author Team A - Bovines - Brady Self
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "User [id=%d, username=%s, password=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;


    /**
     * Create a new User with the given id, username, and password
     * @param id The id of the User
     * @param username The username of the User
     * @param password the password of the User
     *
     *
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If field
     * is not provided it will be set to default java value
     */
    public User(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * an equals function override for user comparison
     * @param user to be compared
     * @return true if equal, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    /**
     * a hashcode generator for a User
     * @return hashcode for User
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }


    /**
     * Getter for password
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password password of user
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * getter for the user id
     * @return The id of the user
     */
    public int getId() {return id;}

    /**
     * Sets the username of the user - necessary for JSON/JAVA deserialization
     * @param username The username of the product
     */
    public void setUserName(String username) {this.username = username;}

    /**
     * gets username of the user
     * @return The username of the user
     */
    public String getUserName() {return username;}

    /*
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, username, password);
    }
}
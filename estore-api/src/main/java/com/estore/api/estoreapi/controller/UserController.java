package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;

/**
 * Handles REST API requests for the Product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Team A - Bovines
 */

@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDAO;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param userDAO The Product Data Access Object to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Responds to GET request for a user for the given id
     * 
     * @param id The id of the user
     * 
     * @return ResponseEntity with user object and HTTP status of OK if found
     * <br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        LOG.info("GET /users/" + id);
        try {
            User user = userDAO.getUser(id);
            if (user != null)
                return new ResponseEntity<User>(user,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all users
     * 
     * @return ResponseEntity with array of user objects (may be empty) and
     * HTTP status of OK
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers() {
        LOG.info("GET /users");
        try {
            return new ResponseEntity<User[]>(userDAO.getUsers(), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all users whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the users
     * 
     * @return ResponseEntity with array of users objects (may be empty) and
     * HTTP status of OK
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all users that contain the text "nameHere"
     * GET http://localhost:8080/users/?username=nameHere
     */
    @GetMapping("/")
    public ResponseEntity<User[]> searchUsers(@RequestParam String username) {
        LOG.info("GET /users/?username="+username);

        try {
            return new ResponseEntity<User[]>(userDAO.searchUsers(username), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a user with the provided user object
     * 
     * @param user - The user to create
     * 
     * @return ResponseEntity with created user object and HTTP status of CREATED
     * <br>
     * ResponseEntity with HTTP status of CONFLICT if products object already exists
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);

        try {
            User created = userDAO.createUser(user);
            if(created != null) {
                return new ResponseEntity<User>(created, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);        
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the user with the provided user object, if it exists
     * 
     * @param user The user to update
     * 
     * @return ResponseEntity with updated user object and
     * HTTP status of OK if updated
     * <br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users " + user);

        try {
            User updated = userDAO.updateUser(user);
            if(updated != null) {
                return new ResponseEntity<User>(updated, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a user with the given id
     * 
     * @param id The id of the user to delete
     * 
     * @return ResponseEntity HTTP status of OK if deleted
     * <br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /users/" + id);

        try {
            boolean del = userDAO.deleteUser(id);
            if(del) {
                return new ResponseEntity<User>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Attempts to log in a User
     * 
     * @param username the username to check
     * @param password the password to check
     * 
     * @return ResponseEntity with User and HTTP status of OK if log in was successful
     * <br>
     * ResponseEntity with HTTP status of NOT_FOUND if username or password is incorrect
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        LOG.info("GET /users/login?username=" + username + "&password=" + password);
        try {
            User loginUser = userDAO.login(username, password);
            if (loginUser != null) {
                return new ResponseEntity<User>(loginUser, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT); 
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Logs out the current user
     * 
     * @return ResponseEntity with HTTP status of Accepted if the user successfully logged out
     * <br>
     * Response entity with INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/logout")
    public ResponseEntity<User> logout() {
        try {
            userDAO.logout();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns currently logged in user
     * 
     * @return ResponseEntity with current User and HTTP status of OK if there is a user logged in
     * <br>
     * ResponseEntity with Guest User and HTTP status of OK
     * <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser() {
        LOG.info("GET /users/currentUser");
        try {
            return new ResponseEntity<User>(userDAO.getCurrentUser(), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns guest user account
     * 
     * @return ResponseEntity with guest user and HTTP status of Ok
     * <br>
     * REseponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/guest")
    public ResponseEntity<User> getGuest(){
        LOG.info("GET /users/guest");
        try {
            return new ResponseEntity<>(userDAO.getGuest(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

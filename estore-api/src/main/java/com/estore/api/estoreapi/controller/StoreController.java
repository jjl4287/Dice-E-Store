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

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.Product;

/**
 * Handles REST API requests for the Product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team A - Bovines
 */

@RestController
@RequestMapping("products")
public class StoreController {
    private static final Logger LOG = Logger.getLogger(StoreController.class.getName());
    private ProductDAO productDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param productDAO The Product Data Access Object to perform CRUD operations
     *                   <br>
     *                   This dependency is injected by the Spring Framework
     */
    public StoreController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Responds to GET request for a product for the given id
     * 
     * @param id The id of the product
     * 
     * @return ResponseEntity with product object and HTTP status of OK if found
     *         <br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = productDAO.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all products
     * 
     * @return ResponseEntity with array of product objects (may be empty) and
     *         HTTP status of OK
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {
        LOG.info("GET /products");
        try {
            return new ResponseEntity<Product[]>(productDAO.getProducts(), HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all products whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             products
     * 
     * @return ResponseEntity with array of products objects (may be empty) and
     *         HTTP status of OK
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all products that contain the text "nameHere"
     *         GET http://localhost:8080/products/?name=nameHere
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String name) {
        LOG.info("GET /products/?name=" + name);

        try {
            return new ResponseEntity<Product[]>(productDAO.searchProducts(name), HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a product with the provided product object
     * 
     * @param product - The products to create
     * 
     * @return ResponseEntity with created products object and HTTP status of
     *         CREATED
     *         <br>
     *         ResponseEntity with HTTP status of CONFLICT if products object
     *         already exists
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);

        try {
            Product created = productDAO.createProduct(product);
            if (created != null) {
                return new ResponseEntity<Product>(created, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the product with the provided product object, if it exists
     * 
     * @param product The products to update
     * 
     * @return ResponseEntity with updated products object and
     *         HTTP status of OK if updated
     *         <br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);

        try {
            Product updated = productDAO.updateProduct(product);
            if (updated != null) {
                return new ResponseEntity<Product>(updated, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a product with the given id
     * 
     * @param id The id of the products to delete
     * 
     * @return ResponseEntity HTTP status of OK if deleted
     *         <br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         <br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);

        try {
            boolean del = productDAO.deleteProduct(id);
            if (del) {
                return new ResponseEntity<Product>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

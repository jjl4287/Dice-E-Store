package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;

/**
 * Provides for JSON File based persistence
 * 
 * {@literal @} Creates single instance of class and injects into others
 *              as needed.
 * 
 * @author Team A - Bovines
 */

@Component
public class ProductFileDAO implements ProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductFileDAO.class.getName());
    Map<Integer,Product> Products;      // Provides a local copy of products so that
                                        // we don't need to read from the file each time
    private ObjectMapper objectMapper;  // Provides conversion between Product
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;          // The next Id to assign to a new Product
    private String filename;            // Filename to read from and write to

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON object
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ProductFileDAO(@Value("${products.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the products from the file
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
     * Generates an array of Products from the tree map
     * 
     * @return  The array of Products, may be empty
     */
    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    /**
     * Generates an array of Products from the tree map for any
     * Products that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the Products
     * in the tree map
     * <br> 
     * if containsText is null there is no filter
     * 
     * @return  The array of Products, may be empty
     */
    private Product[] getProductsArray(String containsText) {
        ArrayList<Product> ProductArrayList = new ArrayList<>();

        for (Product Product : Products.values()) {
            if (containsText == null || Product.getName().contains(containsText)) {
                ProductArrayList.add(Product);
            }
        }

        Product[] ProductArray = new Product[ProductArrayList.size()];
        ProductArrayList.toArray(ProductArray);
        return ProductArray;
    }

    /**
     * Saves the Products from the map into the file as an array of JSON objects
     * 
     * @return true if the Products were written with little to no error.
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] ProductArray = getProductsArray();

        // Translates the Java Objects to JSON objects, then to the file
        // writeValue will throw IOException if error
        // with file or reading from file
        objectMapper.writeValue(new File(filename),ProductArray);
        return true;
    }

    /**
     * Loads Products from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        Products = new TreeMap<>();
        nextId = 0;

        // Reads JSON objects from file into array of Products
        // readValue will throw IOException on error
        Product[] ProductArray = objectMapper.readValue(new File(filename),Product[].class);

        // Add each Product to the tree map and keep track of the greatest id
        for (Product Product : ProductArray) {
            Products.put(Product.getId(),Product);
            if (Product.getId() > nextId)
                nextId = Product.getId();
        }
        // Make the next id one greater than maximum from file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product[] getProducts() {
        synchronized(Products) {
            return getProductsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product[] searchProducts(String containsText) {
        synchronized(Products) {
            return getProductsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) {
        synchronized(Products) {
            if (Products.containsKey(id))
                return Products.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product Product) throws IOException {
        synchronized(Products) {
            // We create a new Product object because the id field is immutable
            // and we need to assign the next unique id
            Product newProduct = new Product(nextId(),Product.getName(), Product.getQty(), 
            Product.getPrice(), Product.getUrl(), Product.getDescription());
            Products.put(newProduct.getId(),newProduct);
            save(); // may throw an IOException
            return newProduct;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product Product) throws IOException {
        synchronized(Products) {
            if (Products.containsKey(Product.getId()) == false)
                return null;  // Product does not exist

            Products.put(Product.getId(),Product);
            save(); // may throw an IOException
            return Product;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized(Products) {
            if (Products.containsKey(id)) {
                Products.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}

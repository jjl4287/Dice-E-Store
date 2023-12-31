package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

/**
 * Unit tests for ProductFileDAO
 *
 * @author Team A - Bovines - Vincent Sarubbi
 */
@Tag("Persistence-tier")
public class ProductFileDAOTests {
    ProductDAO testProductFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(1, "TestProduct1", 10, 10, "url", "Description");
        testProducts[1] = new Product(2, "TestProduct2", 20, 20, "url", "Description");
        testProducts[2] = new Product(3, "TestProduct3", 30, 30, "url", "Description");
        when(mockObjectMapper
                .readValue(new File("mockProductsFile.txt"), Product[].class))
                .thenReturn(testProducts);
        testProductFileDAO = new ProductFileDAO("mockProductsFile.txt", mockObjectMapper);
    }

    @Test
    public void testGetUsers() throws IOException { // getuser may throw IOException
        // Invoke
        Product[] users = testProductFileDAO.getProducts();

        // Analyze
        assertEquals(users.length, testProducts.length);
        for (int i = 0; i < users.length; i++) {
            assertEquals(users[i], testProducts[i]);
        }
    }

    @Test
    public void testFindProducts() throws IOException { // searchProducts may throw IOException
        // Invoke
        Product[] products = testProductFileDAO.searchProducts("1");

        // Analyze
        assertEquals(products.length, 1);
        assertEquals(products[0], products[0]);
    }

    @Test
    public void testGetProduct() throws IOException { // getProducts may throw IOException
        // Invoke
        Product product = testProductFileDAO.getProduct(2);

        // Analzye
        assertEquals(product, testProducts[1]);
    }

    @Test
    public void testCreateProduct() throws IOException { // createProduct may throw IOException
        // Setup
        Product product = new Product(4, "TestProduct4", 40, 40, "url", "Description");

        // Invoke
        Product result = assertDoesNotThrow(() -> testProductFileDAO.createProduct(product));

        // Analyze
        assertNotNull(result);
        Product actual = testProductFileDAO.getProduct(product.getId());
        assertEquals(product, actual);
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product updateProduct = new Product(1, "TestUpdateProduct", 99, 99, "url", "Description");

        // Invoke
        Product result = assertDoesNotThrow(() -> testProductFileDAO.updateProduct(updateProduct));

        // Analyze
        assertNotNull(result);
        Product actual = testProductFileDAO.getProduct(updateProduct.getId());
        assertEquals(updateProduct, actual);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> testProductFileDAO.deleteProduct(2),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(result, true);
    }

    @Test
    public void testGetProductNotFound() throws IOException {
        // Invoke
        Product product = testProductFileDAO.getProduct(69);

        // Analyze
        assertEquals(product, null);
    }

    @Test
    public void testDeleteProductNo() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> testProductFileDAO.deleteProduct(69),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(result, false);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Product product = new Product(69, "newProduct", 69, 69, "url", "Description");

        // Invoke
        Product result = assertDoesNotThrow(() -> testProductFileDAO.updateProduct(product),
                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new ProductFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }
}

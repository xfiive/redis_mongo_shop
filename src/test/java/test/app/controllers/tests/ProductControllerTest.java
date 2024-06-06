package test.app.controllers.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.app.controllers.ProductController;
import test.app.entities.Product;
import test.app.services.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product("1", "Product1", "Description1", "Properties1", 10.0);
        Product product2 = new Product("2", "Product2", "Description2", "Properties2", 20.0);
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.findAll()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(productService, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Product product = new Product("1", "Product1", "Description1", "Properties1", 10.0);
        when(productService.findById(anyString())).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).findById("1");
    }

    @Test
    public void testFindByIdNotFound() {
        when(productService.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).findById("1");
    }

    @Test
    public void testFindAllByName() {
        Product product1 = new Product("1", "Product1", "Description1", "Properties1", 10.0);
        Product product2 = new Product("2", "Product2", "Description2", "Properties2", 20.0);
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.findAllByName(anyString())).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.findAllByName("Product1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(productService, times(1)).findAllByName("Product1");
    }

    @Test
    public void testAddNew() {
        Product product = new Product("1", "Product1", "Description1", "Properties1", 10.0);
        when(productService.addNew(any(Product.class))).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.addNew(product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).addNew(product);
    }

    @Test
    public void testDeleteById() {
        when(productService.deleteById(anyString())).thenReturn(true);

        ResponseEntity<String> response = productController.deleteById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody());
        verify(productService, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(productService.deleteById(anyString())).thenReturn(false);

        ResponseEntity<String> response = productController.deleteById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());
        verify(productService, times(1)).deleteById("1");
    }
}

package test.app.controllers.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.app.controllers.OrderController;
import test.app.entities.Delivery;
import test.app.entities.Order;
import test.app.entities.Product;
import test.app.services.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product("1", "Product1", "Description1", "Properties1", 10.0);
        Product product2 = new Product("2", "Product2", "Description2", "Properties2", 20.0);
        Delivery delivery = new Delivery("1", "123 Street", "2024-06-03", "Courier");

        Order order1 = new Order("1", "Client1", Arrays.asList(product1, product2), delivery, 30.0);
        Order order2 = new Order("2", "Client2", List.of(product1), delivery, 10.0);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderService.findAll()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(orderService, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Product product = new Product("1", "Product1", "Description1", "Properties1", 10.0);
        Delivery delivery = new Delivery("1", "123 Street", "2024-06-03", "Courier");

        Order order = new Order("1", "Client1", List.of(product), delivery, 10.0);

        when(orderService.findById("1")).thenReturn(Optional.of(order));

        ResponseEntity<Order> response = orderController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).findById("1");
    }

    @Test
    public void testFindById_NotFound() {
        when(orderService.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<Order> response = orderController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).findById("1");
    }

    @Test
    public void testAddNew() {
        Product product = new Product("1", "Product1", "Description1", "Properties1", 10.0);
        Delivery delivery = new Delivery("1", "123 Street", "2024-06-03", "Courier");

        Order order = new Order("1", "Client1", List.of(product), delivery, 10.0);

        when(orderService.addNew(order)).thenReturn(Optional.of(order));

        ResponseEntity<Order> response = orderController.addNew(order);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).addNew(order);
    }

    @Test
    public void testDeleteById() {
        when(orderService.deleteById("1")).thenReturn(true);

        ResponseEntity<String> response = orderController.deleteById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order deleted successfully", response.getBody());
        verify(orderService, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteById_NotFound() {
        when(orderService.deleteById("1")).thenReturn(false);

        ResponseEntity<String> response = orderController.deleteById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order not found", response.getBody());
        verify(orderService, times(1)).deleteById("1");
    }
}

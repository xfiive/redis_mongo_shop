package test.app.controllers.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.app.controllers.DeliveryController;
import test.app.entities.Delivery;
import test.app.services.DeliveryService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryController deliveryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Delivery delivery1 = new Delivery("1", "address1", "2024-06-03", "method1");
        Delivery delivery2 = new Delivery("2", "address2", "2024-06-04", "method2");

        List<Delivery> deliveries = Arrays.asList(delivery1, delivery2);

        when(deliveryService.findAll()).thenReturn(deliveries);

        ResponseEntity<List<Delivery>> response = deliveryController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(deliveryService, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Delivery delivery = new Delivery("1", "address1", "2024-06-03", "method1");

        when(deliveryService.findById("1")).thenReturn(Optional.of(delivery));

        ResponseEntity<Delivery> response = deliveryController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(delivery, response.getBody());
        verify(deliveryService, times(1)).findById("1");
    }

    @Test
    public void testFindById_NotFound() {
        when(deliveryService.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<Delivery> response = deliveryController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(deliveryService, times(1)).findById("1");
    }

    @Test
    public void testAddNew() {
        Delivery delivery = new Delivery("1", "address1", "2024-06-03", "method1");

        when(deliveryService.addNew(delivery)).thenReturn(Optional.of(delivery));

        ResponseEntity<Delivery> response = deliveryController.addNew(delivery);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(delivery, response.getBody());
        verify(deliveryService, times(1)).addNew(delivery);
    }

    @Test
    public void testDeleteById() {
        when(deliveryService.deleteById("1")).thenReturn(true);

        ResponseEntity<String> response = deliveryController.deleteById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Delivery deleted successfully", response.getBody());
        verify(deliveryService, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteById_NotFound() {
        when(deliveryService.deleteById("1")).thenReturn(false);

        ResponseEntity<String> response = deliveryController.deleteById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Delivery not found", response.getBody());
        verify(deliveryService, times(1)).deleteById("1");
    }
}

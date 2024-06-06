package test.app.controllers.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.app.controllers.ClientController;
import test.app.entities.Client;
import test.app.services.ClientService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientControllerTest {
    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        Client client1 = new Client("1", "name1", "surname1", "email1", "1");
        Client client2 = new Client("2", "name2", "surname2", "email2", "2");

        List<Client> clients = Arrays.asList(client1, client2);

        when(clientService.findAll()).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(clientService, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Client client1 = new Client("1", "name1", "surname1", "email1", "1");
        when(clientService.findById(anyString())).thenReturn(Optional.of(client1));

        ResponseEntity<Client> response = clientController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client1, response.getBody());
        verify(clientService, times(1)).findById("1");
    }

    @Test
    public void testFindByIdNotFound() {
        when(clientService.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Client> response = clientController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(clientService, times(1)).findById("1");
    }

    @Test
    public void testFindAllByName() {
        Client client1 = new Client("1", "name1", "surname1", "email1", "1");
        Client client2 = new Client("2", "name2", "surname2", "email2", "2");
        List<Client> clients = Arrays.asList(client1, client2);

        when(clientService.findAllByName(anyString())).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.findAllByName("name1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(clientService, times(1)).findAllByName("name1");
    }

    @Test
    public void testAddNew() {
        Client client1 = new Client("1", "name1", "surname1", "email1", "1");

        clientController.addNew(client1);

        verify(clientService, times(1)).addNew(client1);
    }
}

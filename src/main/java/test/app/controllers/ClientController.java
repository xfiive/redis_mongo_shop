package test.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import test.app.entities.Client;
import test.app.services.ClientService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> findAll() {
        List<Client> clients = this.clientService.findAll();
        if (clients.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Client>> findAllByName(@RequestParam String name) {
        List<Client> clients = this.clientService.findAllByName(name);
        if (clients.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/surname")
    public ResponseEntity<List<Client>> findAllBySurname(@RequestParam String surname) {
        List<Client> clients = this.clientService.findAllBySurname(surname);
        if (clients.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable String id) {
        Optional<Client> client = this.clientService.findById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addNew(@RequestBody Client client) {
        Optional<Client> savedClient = this.clientService.addNew(client);
        return savedClient.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client client) {
        Optional<Client> updatedClient = this.clientService.updateClient(id, client);
        return updatedClient.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isRemoved = this.clientService.deleteById(id);
        if (!isRemoved) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }

}

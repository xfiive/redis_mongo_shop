package test.app.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.app.entities.Client;
import test.app.repos.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public List<Client> findAllByName(String name) {
        return clientRepository.findAllByName(name);
    }

    public List<Client> findAllBySurname(String surname) {
        return this.clientRepository.findAllBySurname(surname);
    }

    public Optional<Client> findById(String id) {
        return this.clientRepository.findById(id);
    }

    public Optional<Client> addNew(@NotNull Client client) {
        if (this.clientRepository.findById(client.getId()).isEmpty()) {
            return Optional.of(this.clientRepository.save(client));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Client> updateClient(String id, Client updatedClient) {
        Optional<Client> existingClientOpt = this.clientRepository.findById(id);

        if (existingClientOpt.isEmpty())
            return Optional.empty();

        Client existingClient = existingClientOpt.get();
        existingClient.setId(updatedClient.getId());
        existingClient.setName(updatedClient.getName());
        existingClient.setSurname(updatedClient.getSurname());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setPhone(updatedClient.getPhone());

        Client savedClient = this.clientRepository.save(existingClient);

        return Optional.of(savedClient);
    }

    public boolean deleteById(String id) {
        Optional<Client> client = this.clientRepository.findById(id);

        if (client.isEmpty())
            return false;

        this.clientRepository.deleteById(id);
        return true;
    }
}

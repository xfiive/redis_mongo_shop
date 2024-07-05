package test.app.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.app.entities.Client;
import test.app.repos.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Cacheable(value = "ClientService::findAll", key = "'ClientService::findAll'")
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Transactional
    @Cacheable(value = "ClientService::findAllByName", key = "'ClientService::findAllByName.' + #name")
    public List<Client> findAllByName(String name) {
        return clientRepository.findAllByName(name);
    }

    @Transactional
    @Cacheable(value = "ClientService::findAllBySurname", key = "'ClientService::findAllBySurname.' + #surname")
    public List<Client> findAllBySurname(String surname) {
        return this.clientRepository.findAllBySurname(surname);
    }

    @Transactional
    @Cacheable(value = "ClientService::findById", key = "'ClientService::findById.' + #id")
    public Optional<Client> findById(String id) {
        return this.clientRepository.findById(id);
    }

    @Transactional
    @Cacheable(value = "ClientService::addNew", key = "'ClientService::addNew.' + #client.id")
    public Optional<Client> addNew(@NotNull Client client) {
        if (this.clientRepository.findById(client.getId()).isEmpty()) {
            return Optional.of(this.clientRepository.save(client));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    @CachePut(value = "ClientService::updateClient", key = "'ClientService::updateClient.' + #id")
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

    @Transactional
    @CacheEvict(value = "ClientService::deleteById", key = "'ClientService::deleteById.' + #id")
    public boolean deleteById(String id) {
        Optional<Client> client = this.clientRepository.findById(id);

        if (client.isEmpty())
            return false;

        this.clientRepository.deleteById(id);
        return true;
    }
}

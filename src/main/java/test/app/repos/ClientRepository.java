package test.app.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import test.app.entities.Client;

import java.util.List;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    List<Client> findAllByName(String name);

    List<Client> findAllBySurname(String surname);
}

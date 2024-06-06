package test.app.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.app.entities.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByName(String name);
}

package test.app.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.app.entities.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findAllByClientId(String id);
}

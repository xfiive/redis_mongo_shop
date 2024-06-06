package test.app.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.app.entities.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    List<Delivery> findAllByDeliveryDate(String deliveryDate);

    List<Delivery> findAllByAddress(String address);

    List<Delivery> findAllByDeliveryMethod(String deliveryMethod);
}

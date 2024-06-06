package test.app.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.app.entities.Delivery;
import test.app.repos.DeliveryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private DeliveryRepository deliveryRepository;

    @Autowired
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> findAll() {
        return this.deliveryRepository.findAll();
    }

    public Optional<Delivery> findById(String id) {
        return this.deliveryRepository.findById(id);
    }

    public List<Delivery> findAllByDeliveryDate(String deliveryDate) {
        return this.deliveryRepository.findAllByDeliveryDate(deliveryDate);
    }

    public List<Delivery> findAllByAddress(String address) {
        return this.deliveryRepository.findAllByAddress(address);
    }

    public List<Delivery> findAllByDeliveryMethod(String deliveryMethod) {
        return this.deliveryRepository.findAllByDeliveryMethod(deliveryMethod);
    }

    public Optional<Delivery> addNew(@NotNull Delivery delivery) {
        if (this.deliveryRepository.findById(delivery.getId()).isEmpty())
            return Optional.of(this.deliveryRepository.save(delivery));
        else
            return Optional.empty();
    }

    public Optional<Delivery> updateDelivery(String id, Delivery updatedDelivery) {
        Optional<Delivery> existingDeliveryOpt = this.deliveryRepository.findById(id);

        if (existingDeliveryOpt.isEmpty())
            return Optional.empty();

        Delivery existingDelivery = existingDeliveryOpt.get();
        existingDelivery.setId(updatedDelivery.getId());
        existingDelivery.setAddress(updatedDelivery.getAddress());
        existingDelivery.setDeliveryDate(updatedDelivery.getDeliveryDate());
        existingDelivery.setDeliveryMethod(updatedDelivery.getDeliveryMethod());

        Delivery savedDelivery = this.deliveryRepository.save(existingDelivery);

        return Optional.of(savedDelivery);
    }

    public boolean deleteById(String id) {
        Optional<Delivery> delivery = this.deliveryRepository.findById(id);

        if (delivery.isEmpty())
            return false;

        this.deliveryRepository.deleteById(id);
        return true;
    }

}

package test.app.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Cacheable(value = "DeliveryService::findAll", key = "'DeliveryService::findAll'")
    public List<Delivery> findAll() {
        return this.deliveryRepository.findAll();
    }

    @Transactional
    @Cacheable(value = "DeliveryService::findById", key = "'DeliveryService::findById' + #id")
    public Optional<Delivery> findById(String id) {
        return this.deliveryRepository.findById(id);
    }

    @Transactional
    @Cacheable(value = "DeliveryService::findAllByDeliveryDate", key = "'DeliveryService::findAllByDeliveryDate' + #deliveryDate")
    public List<Delivery> findAllByDeliveryDate(String deliveryDate) {
        return this.deliveryRepository.findAllByDeliveryDate(deliveryDate);
    }

    @Transactional
    @Cacheable(value = "DeliveryService::findAllByAddress", key = "'DeliveryService::findAllByAddress' + #address")
    public List<Delivery> findAllByAddress(String address) {
        return this.deliveryRepository.findAllByAddress(address);
    }

    @Transactional
    @Cacheable(value = "DeliveryService::findAllByDeliveryMethod", key = "'DeliveryService::findAllByDeliveryMethod' + #deliveryMethod")
    public List<Delivery> findAllByDeliveryMethod(String deliveryMethod) {
        return this.deliveryRepository.findAllByDeliveryMethod(deliveryMethod);
    }

    @Transactional
    @Cacheable(value = "DeliveryService::addNew", key = "'DeliveryService::addNew.' + #delivery.id")
    public Optional<Delivery> addNew(@NotNull Delivery delivery) {
        if (this.deliveryRepository.findById(delivery.getId()).isEmpty())
            return Optional.of(this.deliveryRepository.save(delivery));
        else
            return Optional.empty();
    }

    @Transactional
    @CachePut(value = "DeliveryService::updateDelivery", key = "'DeliveryService::updateDelivery.' + #id")
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

    @Transactional
    @CacheEvict(value = "DeliveryService::deleteById", key = "'DeliveryService::deleteById.' + #id")
    public boolean deleteById(String id) {
        Optional<Delivery> delivery = this.deliveryRepository.findById(id);

        if (delivery.isEmpty())
            return false;

        this.deliveryRepository.deleteById(id);
        return true;
    }

}

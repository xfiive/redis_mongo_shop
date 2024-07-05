package test.app.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.app.entities.Order;
import test.app.repos.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Cacheable(value = "OrderService::findAll", key = "'OrderService::findAll'")
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    @Transactional
    @Cacheable(value = "OrderService::findById", key = "'OrderService::findById' + #id")
    public Optional<Order> findById(String id) {
        return this.orderRepository.findById(id);
    }

    @Transactional
    @Cacheable(value = "OrderService::findByClientId", key = "'OrderService::findByClientId' + #id")
    public List<Order> findAllByClientId(String id) {
        return this.orderRepository.findAllByClientId(id);
    }

    @Transactional
    @Cacheable(value = "OrderService::addNew", key = "'OrderService::addNew' + #order.id")
    public Optional<Order> addNew(@NotNull Order order) {
        if (this.orderRepository.findById(order.getId()).isEmpty()) {
            return Optional.of(this.orderRepository.save(order));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    @CachePut(value = "OrderService::updateOrder", key = "'OrderService::updateOrder' + #id")
    public Optional<Order> updateOrder(String id, Order updatedOrder) {
        Optional<Order> existingOrderOpt = this.orderRepository.findById(id);

        if (existingOrderOpt.isEmpty())
            return Optional.empty();

        Order existingOrder = existingOrderOpt.get();
        existingOrder.setId(updatedOrder.getId());
        existingOrder.setClientId(updatedOrder.getClientId());
        existingOrder.setProducts(updatedOrder.getProducts());
        existingOrder.setDeliveryDetails(updatedOrder.getDeliveryDetails());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());

        Order savedOrder = this.orderRepository.save(existingOrder);

        return Optional.of(savedOrder);
    }

    @Transactional
    @CacheEvict(value = "OrderService::deleteById", key = "'OrderService::deleteById' + #id")
    public boolean deleteById(String id) {
        Optional<Order> order = this.orderRepository.findById(id);

        if (order.isEmpty())
            return false;

        this.orderRepository.deleteById(id);
        return true;
    }
}

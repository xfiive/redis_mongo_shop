package test.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import test.app.entities.Order;
import test.app.services.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable String id) {
        Optional<Order> order = this.orderService.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Order>> findAllByClientId(@PathVariable String id) {
        List<Order> orders = this.orderService.findAllByClientId(id);
        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isRemoved = this.orderService.deleteById(id);
        if (!isRemoved)
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addNew(@RequestBody Order order) {
        Optional<Order> savedOrder = this.orderService.addNew(order);
        return savedOrder.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        Optional<Order> updatedOrder = this.orderService.updateOrder(id, order);
        return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

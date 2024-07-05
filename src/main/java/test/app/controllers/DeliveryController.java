package test.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.app.entities.Delivery;
import test.app.services.DeliveryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private DeliveryService deliveryService;

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Delivery>> findAll() {
        List<Delivery> deliveries = this.deliveryService.findAll();
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> findById(@PathVariable String id) {
        Optional<Delivery> delivery = this.deliveryService.findById(id);
        return delivery.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/all/{date}")
    public ResponseEntity<List<Delivery>> findAllByDeliveryDate(@PathVariable String date) {
        List<Delivery> deliveries = this.deliveryService.findAllByDeliveryDate(date);
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/all/{address}")
    public ResponseEntity<List<Delivery>> findAllByAddress(@PathVariable String address) {
        List<Delivery> deliveries = this.deliveryService.findAllByAddress(address);
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/all/{method}")
    public ResponseEntity<List<Delivery>> findAllByDeliveryMethod(@PathVariable String method) {
        List<Delivery> deliveries = this.deliveryService.findAllByDeliveryMethod(method);
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Delivery> addNew(@RequestBody Delivery delivery) {
        Optional<Delivery> savedDelivery = this.deliveryService.addNew(delivery);
        return savedDelivery.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable String id, @RequestBody Delivery delivery) {
        Optional<Delivery> updatedDelivery = this.deliveryService.updateDelivery(id, delivery);
        return updatedDelivery.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isRemoved = this.deliveryService.deleteById(id);
        if (!isRemoved)
            return new ResponseEntity<>("Delivery not found", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>("Delivery deleted successfully", HttpStatus.OK);
    }

}

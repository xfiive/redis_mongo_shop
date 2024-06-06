package test.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import test.app.entities.Product;
import test.app.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = this.productService.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name")
    public ResponseEntity<List<Product>> findAllByName(@RequestParam String name) {
        List<Product> products = this.productService.findAllByName(name);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addNew(@RequestBody Product product) {
        Optional<Product> savedProduct = this.productService.addNew(product);
        return savedProduct.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Optional<Product> updatedProduct = productService.updateProduct(id, product);
        return updatedProduct.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isRemoved = this.productService.deleteById(id);
        if (!isRemoved) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }
}

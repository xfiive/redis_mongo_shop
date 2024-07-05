package test.app.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.app.entities.Product;
import test.app.repos.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Cacheable(value = "ProductService::findAll", key = "'ProductService::findAll'")
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Transactional
    @Cacheable(value = "ProductService::findById", key = "'ProductService::findById' + #id")
    public Optional<Product> findById(String id) {
        return this.productRepository.findById(id);
    }

    @Transactional
    @Cacheable(value = "ProductService::findAllByName", key = "'ProductService::findAllByName' + #name")
    public List<Product> findAllByName(String name) {
        return this.productRepository.findAllByName(name);
    }

    @Transactional
    @Cacheable(value = "ProductService::addNew", key = "'ProductService::addNew' + #product.id")
    public Optional<Product> addNew(@NotNull Product product) {
        if (productRepository.findById(product.getId()).isEmpty()) {
            return Optional.of(productRepository.save(product));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    @Cacheable(value = "ProductService::updateProduct", key = "'ProductService::updateProduct' + #id")
    public Optional<Product> updateProduct(String id, Product updatedProduct) {
        Optional<Product> existingProductOpt = this.productRepository.findById(id);

        if (existingProductOpt.isEmpty())
            return Optional.empty();

        Product existingProduct = existingProductOpt.get();
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setProperties(updatedProduct.getProperties());
        existingProduct.setPrice(updatedProduct.getPrice());

        Product savedProduct = this.productRepository.save(existingProduct);

        return Optional.of(savedProduct);
    }

    @Transactional
    @Cacheable(value = "ProductService::deleteById", key = "'ProductService::deleteById' + #id")
    public boolean deleteById(String id) {
        Optional<Product> product = this.productRepository.findById(id);

        if (product.isEmpty()) {
            return false;
        }

        this.productRepository.deleteById(id);
        return true;
    }
}

package org.example.mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mapper.exceptionhandling.excceptions.NotFoundException;
import org.example.mapper.model.Product;
import org.example.mapper.repository.ProductRepository;
import org.example.mapper.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Продукт с id " + productId + " не найден"));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product updOrder = productRepository.findById(product.getId()).map(prod -> {
            prod.setName(product.getName());
            prod.setDescription(prod.getDescription());
            prod.setPrice(prod.getPrice());
            prod.setQuantityInStock(prod.getQuantityInStock());
            return prod;
        }).orElseThrow(() ->
                new NotFoundException("Продукт с id " + product.getId() + " не найден"));
        return productRepository.save(updOrder);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}

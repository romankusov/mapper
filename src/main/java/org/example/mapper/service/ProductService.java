package org.example.mapper.service;

import org.example.mapper.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer productId);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Integer productId);
}

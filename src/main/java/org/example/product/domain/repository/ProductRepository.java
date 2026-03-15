package org.example.product.domain.repository;

import org.example.product.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Optional<Product> findById(UUID productId);

    List<Product> findAll();

    Product save(Product product);

    void delete(Product product);
}


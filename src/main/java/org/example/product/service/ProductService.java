package org.example.product.service;

import org.example.product.domain.Product;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.UpdateProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAll();

    Product getById(UUID productId);

    Product create(CreateProductRequest request);

    Product update(UUID productId, UpdateProductRequest request);

    void delete(UUID productId);
}

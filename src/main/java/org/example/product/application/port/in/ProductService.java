package org.example.product.application.port.in;

import org.example.product.domain.Product;
import org.example.product.adapter.in.web.dto.CreateProductRequest;
import org.example.product.adapter.in.web.dto.UpdateProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAll();

    Product getById(UUID productId);

    Product create(CreateProductRequest request);

    Product update(UUID productId, UpdateProductRequest request);

    void delete(UUID productId);
}

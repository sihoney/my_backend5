package org.example.product.application.usecase;

import org.example.product.application.dto.CreateProductInput;
import org.example.product.application.dto.UpdateProductInput;
import org.example.product.domain.model.Product;
import org.example.product.presentation.dto.request.CreateProductRequest;
import org.example.product.presentation.dto.request.UpdateProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductUsecase {
    Product create(CreateProductRequest request);

    List<Product> getAll();

    Product getById(UUID productId);

    Product update(UUID productId, UpdateProductRequest request);

    void delete(UUID productId);
}

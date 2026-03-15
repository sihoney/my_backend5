package org.example.product.application.service;

import org.example.product.application.dto.CreateProductInput;
import org.example.product.application.dto.UpdateProductInput;
import org.example.product.domain.repository.ProductRepository;
import org.example.product.domain.model.Product;
import org.example.product.application.usecase.ProductUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductUsecase {

    private final ProductRepository productPersistencePort;

    public ProductServiceImpl(ProductRepository productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public List<Product> getAll() {
        return productPersistencePort.findAll();
    }

    @Override
    public Product getById(UUID productId) {
        return findByIdOrThrow(productId);
    }

    @Transactional
    @Override
    public Product create(CreateProductInput input) {
        Product product = Product.create(
                toUuid(input.sellerId(), "sellerId"),
                input.name(),
                input.description(),
                input.price(),
                input.stock(),
                input.status(),
                toUuid(input.creatorId(), "creatorId")
        );

        return productPersistencePort.save(product);
    }

    @Override
    public Product update(
            UUID productId,
            UpdateProductInput input
    ) {
        Product product = findByIdOrThrow(productId);
        product.update(
                input.name(),
                input.description(),
                input.price(),
                input.stock(),
                input.status(),
                toUuid(input.modifierId(), "modifierId")
        );
        return product;
    }

    @Override
    public void delete(UUID productId) {
        Product product = findByIdOrThrow(productId);
        productPersistencePort.delete(product);
    }

    private Product findByIdOrThrow(UUID productId) {
        return productPersistencePort.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    private UUID toUuid(
            String value,
            String fieldName
    ) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " must be valid UUID");
        }
    }
}

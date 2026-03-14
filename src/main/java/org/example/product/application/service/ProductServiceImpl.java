package org.example.product.application.service;

import org.example.product.application.port.out.ProductPersistencePort;
import org.example.product.domain.Product;
import org.example.product.adapter.in.web.dto.CreateProductRequest;
import org.example.product.adapter.in.web.dto.UpdateProductRequest;
import org.example.product.application.port.in.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductPersistencePort productPersistencePort;

    public ProductServiceImpl(ProductPersistencePort productPersistencePort) {
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
    public Product create(CreateProductRequest request) {
        Product product = Product.create(
                toUuid(request.sellerId(), "sellerId"),
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.status(),
                toUuid(request.creatorId(), "creatorId")
        );

        return productPersistencePort.save(product);
    }

    @Override
    public Product update(
            UUID productId,
            UpdateProductRequest request
    ) {
        Product product = findByIdOrThrow(productId);
        product.update(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.status(),
                toUuid(request.modifierId(), "modifierId")
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

package org.example.product.service;

import lombok.RequiredArgsConstructor;
import org.example.product.domain.Product;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.UpdateProductRequest;
import org.example.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
//@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
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

        return productRepository.save(product);
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
        productRepository.delete(product);
    }

    private Product findByIdOrThrow(UUID productId) {
        return productRepository.findById(productId)
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

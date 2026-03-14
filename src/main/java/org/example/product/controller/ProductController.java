package org.example.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.product.domain.Product;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.UpdateProductRequest;
import org.example.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
//@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{productId}")
    public Product getById(
            @PathVariable UUID productId
    ) {
        return productService.getById(productId);
    }

    @PostMapping
    public ResponseEntity<Product> create(
            @RequestBody CreateProductRequest request
    ) {
        Product response = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{productId}")
    public Product update(
            @PathVariable UUID productId,
            @RequestBody UpdateProductRequest request
    ) {
        return productService.update(productId, request);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID productId
    ) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}

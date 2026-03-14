package org.example.product.dto;

import java.math.BigDecimal;

public record CreateProductRequest (
        String sellerId,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String status,
        String creatorId
){
}

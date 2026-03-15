package org.example.product.presentation.dto.request;

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

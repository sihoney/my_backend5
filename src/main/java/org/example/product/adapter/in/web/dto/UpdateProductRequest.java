package org.example.product.adapter.in.web.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String status,
        String modifierId
) {
}

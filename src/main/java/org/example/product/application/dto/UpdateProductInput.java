package org.example.product.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductInput(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String status,
        String modifierId
) {
}

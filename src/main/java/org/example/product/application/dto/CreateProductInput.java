package org.example.product.application.dto;

import org.example.product.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

//Product created = productUsecase.create(new CreateProductInput(
//        request.sellerId(),
//        request.name(),
//        request.description(),
//        request.price(),
//        request.stock(),
//        request.status(),
//        actorId
//));

public record CreateProductInput(
        String sellerId,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String status,
        String creatorId
){
}

//public record CreateProductRequest (
//        String sellerId,
//        String name,
//        String description,
//        BigDecimal price,
//        Integer stock,
//        String status,
//        String creatorId
//){
//}

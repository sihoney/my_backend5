package org.example.order.application.usecase;

import org.example.order.presentation.dto.request.CreateOrderRequest;
import org.example.order.presentation.dto.response.OrderResponse;

import java.time.LocalDate;
import java.util.List;

public interface OrderUseCase {

    OrderResponse create(CreateOrderRequest request);

    List<OrderResponse> findAll();

    List<OrderResponse> findSettlementCandidates(LocalDate settlementDate);

}

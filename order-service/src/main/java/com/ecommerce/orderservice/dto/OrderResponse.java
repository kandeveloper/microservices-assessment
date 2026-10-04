package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderResponse {
    private UUID id;
    private String tenantId;
    private String customerEmail;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
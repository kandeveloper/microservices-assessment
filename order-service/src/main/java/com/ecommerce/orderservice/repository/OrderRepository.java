package com.ecommerce.orderservice.repository;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Always ensure queries enforce tenantId matching
    List<Order> findByTenantId(String tenantId);

    Optional<Order> findByIdAndTenantId(UUID id, String tenantId);

    List<Order> findByTenantIdAndStatus(String tenantId, OrderStatus status);
}
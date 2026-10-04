package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.tenant.TenantContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
public class NotificationRestClient {

    private final RestClient restClient;

    public NotificationRestClient(@Value("${notification.service.url:http://localhost:8081}") String notificationServiceUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(notificationServiceUrl)
                .build();
    }

    public void sendNotification(UUID orderId, String eventType, String customerEmail, BigDecimal amount) {
        String currentTenant = TenantContext.getTenantId();

        NotificationRequest request = new NotificationRequest(orderId, eventType, customerEmail, amount);

        try {
            restClient.post()
                    .uri("/api/notifications")
                    .header("X-Tenant-ID", currentTenant)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            log.info("Successfully sent notification event [{}] for order [{}] under tenant [{}]", eventType, orderId, currentTenant);
        } catch (Exception e) {
            // In a production setup with Resilience4j, circuit breaking/retry would handle this.
            log.error("Failed to send notification for order [{}]: {}", orderId, e.getMessage());
        }
    }

    @Data
    public static class NotificationRequest {
        private final UUID orderId;
        private final String eventType;
        private final String customerEmail;
        private final BigDecimal amount;
    }
}
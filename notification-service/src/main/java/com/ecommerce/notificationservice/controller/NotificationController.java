package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.ecommerce.notificationservice.model.NotificationLog;
import com.ecommerce.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> receiveOrderEvent(@Valid @RequestBody NotificationRequest request) {
        notificationService.processNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<NotificationLog>> getNotificationLogs() {
        List<NotificationLog> logs = notificationService.getNotificationsForCurrentTenant();
        return ResponseEntity.ok(logs);
    }
}
package com.cts.taxeasemanagement.controller;


import com.cts.taxeasemanagement.dto.requestdto.BroadcastRequest;
import com.cts.taxeasemanagement.dto.requestdto.DirectNotificationRequest;
import com.cts.taxeasemanagement.dto.responsedto.NotificationResponse;
import com.cts.taxeasemanagement.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/broadcast")
    public ResponseEntity<String> broadcast(@RequestBody BroadcastRequest request) {
        log.info("START: Broadcasting notification: {}", request.getCategory());
        notificationService.broadcastNotification(request.getMessage(), request.getCategory());
        log.info("END: Broadcast notification sent");
        return ResponseEntity.ok("Broadcast notification sent successfully to all users.");
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<String> markNotificationAsRead(
            @PathVariable Long notificationId,
            @RequestParam Long userId) {

        log.info("START: Mark notification {} as read for user {}", notificationId, userId);
        notificationService.markAsRead(notificationId, userId);
        log.info("END: Notification marked as read");
        return ResponseEntity.ok("Notification successfully marked as read.");
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PathVariable Long userId) {
        log.info("START: Fetching notifications for user {}", userId);
        List<NotificationResponse> response = notificationService.getUserNotifications(userId);
        log.info("END: Retrieved {} notifications", response.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> sendDirectNotification(
            @PathVariable Long userId,
            @RequestBody DirectNotificationRequest request) {

        log.info("START: Sending direct notification to user {}", userId);
        notificationService.sendNotificationToUser(userId, request.getMessage(), request.getCategory());
        log.info("END: Direct notification sent");
        return ResponseEntity.ok("Notification sent successfully to user " + userId);
    }
}

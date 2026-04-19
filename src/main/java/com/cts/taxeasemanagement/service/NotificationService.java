package com.cts.taxeasemanagement.service;


import com.cts.taxeasemanagement.dto.responsedto.NotificationResponse;
import com.cts.taxeasemanagement.entity.entityEnum.NotificationCategory;

import java.util.List;

public interface NotificationService {

    /**
     * Broadcasts a single notification message to all active users in the system.
     *
     * @param message  The content of the notification.
     * @param category The category of the notification (defaults to BROADCAST if null).
     */
    void broadcastNotification(String message, NotificationCategory category);

    /**
     * Marks a specific notification as READ for a specific user.
     *
     * @param notificationId The ID of the notification to update.
     * @param userId         The ID of the user requesting the update (for security).
     */
    void markAsRead(Long notificationId, Long userId);

    // NEW: Retrieve all notifications for a specific user
    List<NotificationResponse> getUserNotifications(Long userId);

    void sendNotificationToUser(Long userId, String message, NotificationCategory category);

}
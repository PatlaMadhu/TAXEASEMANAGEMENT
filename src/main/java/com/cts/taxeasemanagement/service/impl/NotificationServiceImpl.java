package com.cts.taxeasemanagement.service.impl;


import com.cts.taxeasemanagement.dao.NotificationRepository;
import com.cts.taxeasemanagement.dao.UserRepository;
import com.cts.taxeasemanagement.dto.responsedto.NotificationResponse;
import com.cts.taxeasemanagement.entity.Notification;
import com.cts.taxeasemanagement.entity.User;
import com.cts.taxeasemanagement.entity.entityEnum.NotificationCategory;
import com.cts.taxeasemanagement.entity.entityEnum.NotificationStatus;
import com.cts.taxeasemanagement.service.AuditLogService;
import com.cts.taxeasemanagement.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;


    @Override
    @Transactional
    public void broadcastNotification(String message, NotificationCategory category) {

        List<User> allUsers = userRepository.findAll();


        List<Notification> notifications = allUsers.stream().map(user ->
                Notification.builder()
                        .user(user)
                        .message(message)
                        .category(category != null ? category : NotificationCategory.BROADCAST)
                        .status(NotificationStatus.UNREAD)
                        .build()
        ).collect(Collectors.toList());


        notificationRepository.saveAll(notifications);
        auditLogService.record("NOTIFICATION_BROADCAST", "notifications/bulk");
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        // 1. Fetch the notification, ensuring it belongs to the user
        Notification notification = notificationRepository.findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new RuntimeException("Notification not found or you do not have permission to access it."));


        if (notification.getStatus() == NotificationStatus.READ) {
            return;
        }


        notification.setStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
        auditLogService.record("NOTIFICATION_SEND", "notifications/user/" + userId);
    }

    @Override
    public List<NotificationResponse> getUserNotifications(Long userId) {
        // 1. Fetch from DB
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedDateDesc(userId);


        return notifications.stream().map(notification ->
                NotificationResponse.builder()
                        .id(notification.getId())
                        .message(notification.getMessage())
                        .category(notification.getCategory())
                        .status(notification.getStatus())
                        .entityId(notification.getEntityId())
                        .createdDate(notification.getCreatedDate())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void sendNotificationToUser(Long userId, String message, NotificationCategory category) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));


        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .category(category != null ? category : NotificationCategory.SYSTEM_UPDATE)
                .status(NotificationStatus.UNREAD)
                .build();


        notificationRepository.save(notification);
    }
}

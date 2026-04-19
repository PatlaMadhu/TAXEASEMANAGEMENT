package com.cts.taxeasemanagement.dao;


import com.cts.taxeasemanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Finds a notification by its ID, but ONLY if it belongs to the given user ID
    Optional<Notification> findByIdAndUserId(Long notificationId, Long userId);
    List<Notification> findByUserIdOrderByCreatedDateDesc(Long userId);
}
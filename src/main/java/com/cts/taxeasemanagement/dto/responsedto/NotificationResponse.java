package com.cts.taxeasemanagement.dto.responsedto;


import com.cts.taxeasemanagement.entity.entityEnum.NotificationCategory;
import com.cts.taxeasemanagement.entity.entityEnum.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String message;
    private NotificationCategory category;
    private NotificationStatus status;
    private Long entityId;
    private Instant createdDate;
}
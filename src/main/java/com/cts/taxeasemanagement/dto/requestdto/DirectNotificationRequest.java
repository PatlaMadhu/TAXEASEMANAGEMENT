package com.cts.taxeasemanagement.dto.requestdto;


import com.cts.taxeasemanagement.entity.entityEnum.NotificationCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectNotificationRequest {

    @NotBlank(message = "Notification message cannot be empty")
    @Size(max = 500, message = "Message cannot exceed 500 characters")
    private String message;

    @NotNull(message = "Notification category is required")
    private NotificationCategory category;
}
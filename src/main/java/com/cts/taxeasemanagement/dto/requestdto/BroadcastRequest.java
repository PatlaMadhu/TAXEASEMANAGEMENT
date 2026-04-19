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
public class BroadcastRequest {

    @NotBlank(message = "Broadcast message cannot be empty")
    @Size(min = 10, max = 1000, message = "Broadcast message must be between 10 and 1000 characters")
    private String message;

    @NotNull(message = "Notification category is required for broadcasting")
    private NotificationCategory category;
}

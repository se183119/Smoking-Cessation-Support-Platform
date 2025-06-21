package com.swpteam.smokingcessation.domain.dto.message;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequest {

    @NotBlank(message = "MESSAGE_CONTENT_REQUIRED")
    String content;

    public class RefreshTokenRequest {
        @NotBlank(message = "REFRESH_TOKEN_REQUIRED")
        String refreshToken;

    }
}

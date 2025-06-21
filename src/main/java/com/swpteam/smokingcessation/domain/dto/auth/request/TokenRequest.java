package com.swpteam.smokingcessation.domain.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenRequest {
    String token;

    public class TokenRefreshRequest {
        @NotBlank(message = "REFRESH_TOKEN_REQUIRED")
        String refreshToken;
    }
}

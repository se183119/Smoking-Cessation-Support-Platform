package com.swpteam.smokingcessation.domain.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {
    @NotBlank(message = "RESET_TOKEN_REQUIRED")
    String token;

    @NotBlank(message = "MESSAGE_REQUIRED")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String newPassword;
}

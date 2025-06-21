package com.swpteam.smokingcessation.domain.dto.account;

import com.swpteam.smokingcessation.validation.DifferentPasswords;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@DifferentPasswords
public class ChangePasswordRequest {
    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    String oldPassword;

    @Size(min = 8, message = "PASSWORD_INVALID")
    @NotBlank(message = "PASSWORD_REQUIRED")
    String newPassword;
}

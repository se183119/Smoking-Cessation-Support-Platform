package net.qsmoke.team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Login request payload")
public class LoginRequest {

@NotBlank(message = "Email is required")
@Email(message = "Invalid email format")
private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
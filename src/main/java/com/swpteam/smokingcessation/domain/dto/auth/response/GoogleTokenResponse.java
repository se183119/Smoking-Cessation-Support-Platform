package com.swpteam.smokingcessation.domain.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoogleTokenResponse {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("id_token")
    String idToken;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("expires_in")
    Integer expiresIn;
    String scope;
}

package com.swpteam.smokingcessation.feature.integration.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeSubscriptionRequest {

    @NotBlank(message = "ACCOUNT_REQUIRED")
    String accountId;

    @NotBlank(message = "MEMBERSHIP_NAME_REQUIRE")
    @Size(min = 1, message = "MEMBERSHIP_MIN_SIZE")
    String membershipName;
}

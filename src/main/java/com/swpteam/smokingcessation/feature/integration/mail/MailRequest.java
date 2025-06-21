package com.swpteam.smokingcessation.feature.integration.mail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailRequest {
    String accountId;
    String subscriptionId;
    double amount;
}

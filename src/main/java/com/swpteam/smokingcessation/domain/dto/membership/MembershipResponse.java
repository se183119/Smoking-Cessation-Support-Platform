package com.swpteam.smokingcessation.domain.dto.membership;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swpteam.smokingcessation.domain.enums.Currency;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MembershipResponse {

    String id;
    String name;
    int durationDays;
    double price;
    Currency currency;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

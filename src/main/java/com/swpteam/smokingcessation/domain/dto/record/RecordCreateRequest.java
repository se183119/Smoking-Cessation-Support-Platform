package com.swpteam.smokingcessation.domain.dto.record;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecordCreateRequest {

    @NotBlank(message = "ACCOUNT_REQUIRED")
    String accountId;

    @NotBlank(message = "CIGARETTES_SMOKED_REQUIRED")
    @Min(value = 0, message = "CIGARETTES_SMOKED_INVALID")
    Integer cigarettesSmoked;

    @NotNull(message = "RECORD_DATE_REQUIRED")
    @FutureOrPresent(message = "RECORD_DATE_INVALID")
    LocalDate date;
}
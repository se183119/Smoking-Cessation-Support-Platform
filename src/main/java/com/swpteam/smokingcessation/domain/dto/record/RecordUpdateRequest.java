package com.swpteam.smokingcessation.domain.dto.record;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecordUpdateRequest {
    @Min(value = 0, message = "RECORD_CIGARETTES_SMOKED_INVALID")
    int cigarettesSmoked;
}
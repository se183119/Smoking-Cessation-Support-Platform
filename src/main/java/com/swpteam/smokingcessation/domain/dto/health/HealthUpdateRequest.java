package com.swpteam.smokingcessation.domain.dto.health;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthUpdateRequest {

    @Min(value = 0, message = "CIGARETTES_PER_DAY_INVALID")
    Integer cigarettesPerDay;

    @Min(value = 0, message = "CIGARETTES_PER_PACK_INVALID")
    Integer cigarettesPerPack;

    @Min(value = 0, message = "FND_LEVEL_INVALID_MIN")
    @Max(value = 10, message = "FND_LEVEL_INVALID_MAX")
    Integer fndLevel;

    @DecimalMin(value = "0.0", message = "PACK_PRICE_INVALID")
    @DecimalMax(value = "500.0", message = "PACK_PRICE_TOO_HIGH")
    Double packPrice;

    @NotBlank(message = "REASON_TO_QUIT_REQUIRED")
    @Size(max = 255, message = "REASON_TO_QUIT_TOO_LONG")
    String reasonToQuit;

    @Min(value = 0, message = "SMOKE_YEAR_INVALID")
    Integer smokeYear;
}

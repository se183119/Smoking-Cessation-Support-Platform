package com.swpteam.smokingcessation.domain.dto.health;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthCreateRequest {

    @NotBlank(message = "ACCOUNT_REQUIRED")
    String accountId;

    @NotNull(message = "CIGARETTES_PER_DAY_REQUIRED")
    @Min(value = 0, message = "CIGARETTES_PER_DAY_INVALID")
    Integer cigarettesPerDay;

    @NotNull(message = "CIGARETTES_PER_PACK_REQUIRED")
    @Min(value = 0, message = "CIGARETTES_PER_PACK_INVALID")
    Integer cigarettesPerPack;

    @NotNull(message = "PACK_PRICE_REQUIRED")
    @DecimalMin(value = "0.0", message = "PACK_PRICE_INVALID")
    @DecimalMax(value = "500.0", message = "PACK_PRICE_TOO_HIGH")
    Double packPrice;

    @Min(value = 0, message = "FND_LEVEL_INVALID_MIN")
    @Max(value = 10, message = "FND_LEVEL_INVALID_MAX")
    Integer fndLevel;

    @NotBlank(message = "REASON_TO_QUIT_REQUIRED")
    @Size(max = 255, message = "REASON_TO_QUIT_TOO_LONG")
    String reasonToQuit;

    @Max(value = 100, message = "SMOKE_YEAR_INVALID")
    Integer smokeYear;
}

package com.swpteam.smokingcessation.feature.integration.currency;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyApiConfig {

    @NonFinal
    @Value("${currency.api.url}")
    String apiUrl;

    @NonFinal
    @Value("${currency.api.key}")
    String apiKey;

    public String getApiEndpoint(String baseCurrency) {
        return String.format("%s/%s/latest/%s", apiUrl, apiKey, baseCurrency);
    }
}


package com.swpteam.smokingcessation.schedule;

import com.swpteam.smokingcessation.feature.integration.currency.CurrencyRateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RateUpdater {

    CurrencyRateService currencyRateService;

    @Scheduled(fixedRate = 86400000)
    public void fetchRates() {
        log.info("Scheduled task started: fetching currency rates for USD");

        currencyRateService.updateRates("USD");

        log.info("Successfully updated currency rates for USD");
    }
}


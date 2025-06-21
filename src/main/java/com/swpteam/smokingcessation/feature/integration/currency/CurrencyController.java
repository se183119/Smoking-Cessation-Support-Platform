package com.swpteam.smokingcessation.feature.integration.currency;

import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.exception.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyController {

    CurrencyRateService currencyRateService;

    @GetMapping("/convert")
    public ResponseEntity<Double> convert(
            @RequestParam double amount,
            @RequestParam String to) {
        Double rate = currencyRateService.getRate(to.toUpperCase());
        if (rate == null)
            throw new AppException(ErrorCode.INVALID_CURRENCY);

        return ResponseEntity.ok(amount * rate);
    }
}


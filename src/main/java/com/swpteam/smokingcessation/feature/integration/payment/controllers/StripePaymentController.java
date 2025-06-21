package com.swpteam.smokingcessation.feature.integration.payment.controllers;

import com.swpteam.smokingcessation.feature.integration.payment.dto.StripeResponse;
import com.swpteam.smokingcessation.feature.integration.payment.dto.StripeSubscriptionRequest;
import com.swpteam.smokingcessation.feature.integration.payment.services.StripeService;
import com.swpteam.smokingcessation.common.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/stripe-payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StripePaymentController {

    StripeService stripeService;

    @PostMapping("/checkout")
    public ApiResponse<StripeResponse> checkoutSubscription(@RequestBody StripeSubscriptionRequest request) {
        return ApiResponse.<StripeResponse>builder()
                .message("Checkout Success")
                .result(stripeService.checkoutSubscription(request))
                .build();
    }

}

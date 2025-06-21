package com.swpteam.smokingcessation.feature.api.v1.membership;

import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionResponse;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.feature.service.interfaces.membership.SubscriptionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionController {

    SubscriptionService subscriptionService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<SubscriptionResponse>>> getSubscriptionPage(@Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<Page<SubscriptionResponse>>builder()
                        .code(SuccessCode.SUBSCRIPTION_GET_ALL.getCode())
                        .message(SuccessCode.SUBSCRIPTION_GET_ALL.getMessage())
                        .result(subscriptionService.getSubscriptionPage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<SubscriptionResponse>> getSubscriptionById(@PathVariable String id) {
        return ResponseEntity.ok().body(
                ApiResponse.<SubscriptionResponse>builder()
                        .code(SuccessCode.SUBSCRIPTION_GET_BY_ID.getCode())
                        .message(SuccessCode.SUBSCRIPTION_GET_BY_ID.getMessage())
                        .result(subscriptionService.getSubscriptionById(id))
                        .build()
        );
    }

    @GetMapping("/account/{id}")
    ResponseEntity<ApiResponse<Page<SubscriptionResponse>>> getSubscriptionPageByAccountId(@PathVariable String id, @Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<SubscriptionResponse>>builder()
                        .code(SuccessCode.SUBSCRIPTION_GET_BY_ACCOUNT.getCode())
                        .message(SuccessCode.SUBSCRIPTION_GET_BY_ACCOUNT.getMessage())
                        .result(subscriptionService.getSubscriptionPageByAccountId(id, request))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<SubscriptionResponse>> createSubscription(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<SubscriptionResponse>builder()
                        .code(SuccessCode.SUBSCRIPTION_CREATED.getCode())
                        .message(SuccessCode.SUBSCRIPTION_CREATED.getMessage())
                        .result(subscriptionService.createSubscription(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<SubscriptionResponse>> updateSubscription(@PathVariable String id, @RequestBody @Valid SubscriptionRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<SubscriptionResponse>builder()
                        .code(SuccessCode.SUBSCRIPTION_UPDATED.getCode())
                        .message(SuccessCode.SUBSCRIPTION_UPDATED.getMessage())
                        .result(subscriptionService.updateSubscription(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deleteSubscription(@PathVariable String id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok().body(
                ApiResponse.<String>builder()
                        .code(SuccessCode.SUBSCRIPTION_DELETED.getCode())
                        .message(SuccessCode.SUBSCRIPTION_DELETED.getMessage())
                        .build()
        );
    }
}

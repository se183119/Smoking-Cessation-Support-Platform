package com.swpteam.smokingcessation.feature.api.v1.membership;

import com.swpteam.smokingcessation.domain.dto.membership.MembershipCreateRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipCurrencyUpdateRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipResponse;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipUpdateRequest;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.feature.service.interfaces.membership.MembershipService;
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
@RequestMapping("/api/v1/memberships")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MembershipController {

    MembershipService membershipService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<MembershipResponse>>> getMembershipPage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<MembershipResponse>>builder()
                        .code(SuccessCode.MEMBERSHIP_GET_ALL.getCode())
                        .message(SuccessCode.MEMBERSHIP_GET_ALL.getMessage())
                        .result(membershipService.getMembershipPage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<MembershipResponse>> getMembershipById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.<MembershipResponse>builder()
                        .code(SuccessCode.MEMBERSHIP_GET_BY_ID.getCode())
                        .message(SuccessCode.MEMBERSHIP_GET_BY_ID.getMessage())
                        .result(membershipService.getMembershipById(id))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<MembershipResponse>> createMembership(@RequestBody @Valid MembershipCreateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<MembershipResponse>builder()
                        .code(SuccessCode.MEMBERSHIP_CREATED.getCode())
                        .message(SuccessCode.MEMBERSHIP_CREATED.getMessage())
                        .result(membershipService.createMembership(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<MembershipResponse>> updateMembership(@PathVariable String id, @RequestBody @Valid MembershipUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<MembershipResponse>builder()
                        .code(SuccessCode.MEMBERSHIP_UPDATED.getCode())
                        .message(SuccessCode.MEMBERSHIP_UPDATED.getMessage())
                        .result(membershipService.updateMembership(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deleteMembership(@PathVariable String id) {
        membershipService.softDeleteMembershipById(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(SuccessCode.MEMBERSHIP_DELETED.getCode())
                        .message(SuccessCode.MEMBERSHIP_DELETED.getMessage())
                        .build()
        );
    }

    @PutMapping("/currency/{id}")
    ResponseEntity<ApiResponse<MembershipResponse>> updateMembershipCurrency(@PathVariable String id, @RequestBody @Valid MembershipCurrencyUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<MembershipResponse>builder()
                        .code(SuccessCode.MEMBERSHIP_UPDATED.getCode())
                        .message(SuccessCode.MEMBERSHIP_UPDATED.getMessage())
                        .result(membershipService.updateMembershipCurrency(id, request))
                        .build()
        );
    }
}

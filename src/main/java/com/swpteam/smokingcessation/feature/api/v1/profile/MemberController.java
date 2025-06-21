package com.swpteam.smokingcessation.feature.api.v1.profile;

import com.swpteam.smokingcessation.domain.dto.member.MemberCreateRequest;
import com.swpteam.smokingcessation.domain.dto.member.MemberResponse;
import com.swpteam.smokingcessation.domain.dto.member.MemberUpdateRequest;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.feature.service.interfaces.profile.MemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {

    MemberService memberService;

    @PostMapping("/{accountId}")
    ResponseEntity<ApiResponse<MemberResponse>> createMember(@PathVariable("accountId") String accountId, @RequestBody @Valid MemberCreateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<MemberResponse>builder()
                        .code(SuccessCode.MEMBER_CREATED.getCode())
                        .message(SuccessCode.MEMBER_CREATED.getMessage())
                        .result(memberService.createMember(request, accountId))
                        .build());
    }

    @GetMapping
    ResponseEntity<ApiResponse<Page<MemberResponse>>> getUsers(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<MemberResponse>>builder()
                        .result(memberService.getMembers(request))
                        .build());
    }

    @GetMapping("/{accountId}")
    ResponseEntity<ApiResponse<MemberResponse>> getMemberById(@PathVariable("accountId") String id) {
        return ResponseEntity.ok(
                ApiResponse.<MemberResponse>builder()
                        .result(memberService.getMemberById(id))
                        .build());
    }

    @PutMapping("/{accountId}")
    ResponseEntity<ApiResponse<MemberResponse>> updateAccount(@PathVariable("accountId") String id, @RequestBody MemberUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<MemberResponse>builder()
                        .code(SuccessCode.ACCOUNT_UPDATED.getCode())
                        .message(SuccessCode.ACCOUNT_UPDATED.getMessage())
                        .result(memberService.updateMember(request, id))
                        .build());
    }
}

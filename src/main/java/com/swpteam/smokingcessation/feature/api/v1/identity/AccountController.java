package com.swpteam.smokingcessation.feature.api.v1.identity;

import com.swpteam.smokingcessation.domain.dto.account.AccountRequest;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.dto.account.AccountUpdateRequest;
import com.swpteam.smokingcessation.domain.dto.account.ChangePasswordRequest;
import com.swpteam.smokingcessation.domain.enums.Role;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.feature.service.interfaces.identity.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/accounts")
class AccountController {

    AccountService accountService;

    @PostMapping
    ResponseEntity<ApiResponse<AccountResponse>> createAccount(@RequestBody @Valid AccountRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .code(SuccessCode.ACCOUNT_CREATED.getCode())
                        .message(SuccessCode.ACCOUNT_CREATED.getMessage())
                        .result(accountService.createAccount(request))
                        .build());
    }

    @GetMapping
    ResponseEntity<ApiResponse<Page<AccountResponse>>> getUsers(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<AccountResponse>>builder()
                        .result(accountService.getAccounts(request))
                        .build());
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .result(accountService.getAccountById(id))
                        .build());
    }

    @PutMapping("/{id}/role")
    ResponseEntity<ApiResponse<AccountResponse>> updateAccountRole(@PathVariable String id, @RequestParam Role role) {
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .code(SuccessCode.ROLE_UPDATED.getCode())
                        .message(SuccessCode.ROLE_UPDATED.getMessage())
                        .result(accountService.updateAccountRole(id, role))
                        .build());
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<AccountResponse>> updateAccount(@PathVariable String id, @RequestBody AccountUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .code(SuccessCode.ACCOUNT_UPDATED.getCode())
                        .message(SuccessCode.ACCOUNT_UPDATED.getMessage())
                        .result(accountService.updateAccountWithoutRole(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(SuccessCode.ACCOUNT_DELETED.getCode())
                        .message(SuccessCode.ACCOUNT_DELETED.getMessage())
                        .build());
    }

    @PostMapping("/change-password")
    ResponseEntity<ApiResponse<AccountResponse>> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .code(SuccessCode.PASSWORD_CHANGE_SUCCESS.getCode())
                        .message(SuccessCode.PASSWORD_CHANGE_SUCCESS.getMessage())
                        .result(accountService.changePassword(request))
                        .build());
    }

    @GetMapping("/me")
    ResponseEntity<ApiResponse<AccountResponse>> getMe(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .result(accountService.getAccountByEmail(email))
                        .build());
    }

    @PutMapping("/ban/{id}")
    ResponseEntity<ApiResponse<Void>> banAccount(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        accountService.banAccount(id, jwt);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.ACCOUNT_BANNED.getCode())
                        .message(SuccessCode.ACCOUNT_BANNED.getMessage())
                        .build());
    }
}

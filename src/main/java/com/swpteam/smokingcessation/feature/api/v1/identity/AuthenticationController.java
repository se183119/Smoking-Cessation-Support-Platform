package com.swpteam.smokingcessation.feature.api.v1.identity;

import com.nimbusds.jose.JOSEException;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.dto.auth.request.*;
import com.swpteam.smokingcessation.domain.dto.auth.response.AuthenticationResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.GoogleTokenResponse;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.feature.service.interfaces.identity.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/google/login")
    ResponseEntity<ApiResponse<GoogleTokenResponse>> getGoogleToken(@RequestBody GoogleTokenRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<GoogleTokenResponse>builder()
                        .result(authenticationService.getGoogleToken(request))
                        .build());
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok()
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .result(authenticationService.authenticate(request))
                        .build());
    }

    @PostMapping("/refresh")
    ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@AuthenticationPrincipal Jwt jwt) throws ParseException, JOSEException {
        String token = jwt.getTokenValue();
        return ResponseEntity.ok()
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .result(authenticationService.refreshToken(token))
                        .build());
    }

    @PostMapping("/register")
    ResponseEntity<ApiResponse<AccountResponse>> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .result(authenticationService.register(request))
                        .build());
    }


    @PostMapping("/forgot-password")
    ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        authenticationService.sendResetPasswordEmail(request.getEmail());
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .result("Reset password link sent to your email if it exists in our system.")
                        .build());
    }

    @PostMapping("/reset-password")
    ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authenticationService.resetPassword(request);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .result("Password has been reset successfully.")
                        .build());
    }

    //front-end job
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@AuthenticationPrincipal Jwt jwt) throws JOSEException, ParseException {
        String token = jwt.getTokenValue();
        authenticationService.logout(token);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .result("Logout successful")
                        .build()
        );
    }


}

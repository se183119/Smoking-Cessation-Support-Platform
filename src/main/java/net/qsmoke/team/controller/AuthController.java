package net.qsmoke.team.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import net.qsmoke.team.dto.AuthResponse;
import net.qsmoke.team.dto.LoginRequest;
import net.qsmoke.team.dto.RegisterRequest;
import net.qsmoke.team.security.JwtTokenProvider;
import net.qsmoke.team.service.RefreshTokenService;
import net.qsmoke.team.service.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import net.qsmoke.team.service.AuthService;

import java.time.Instant;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private TokenBlacklistService tokenBlacklistService;
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
//    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns JWT token along with user details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully authenticated"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials"
    )

    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req
    /*@RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestBody(required = false) LoginRequest loginRequest*/) {
        return ResponseEntity.ok(authService.login(req));
//        if (loginRequest != null) {
//            return ResponseEntity.ok(authService.login(loginRequest));
//        } else if (email != null && password != null) {
//            LoginRequest req = new LoginRequest();
//            req.setEmail(email);
//            req.setPassword(password);
//            return ResponseEntity.ok(authService.login(req));
//        }
//        throw new IllegalArgumentException("Either request parameters or request body must be provided");
    }
//    public String login(@RequestParam String username,
//                        @RequestParam String password) {
//
//        Authentication auth = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        if (auth.isAuthenticated()) {
//            return jwtTokenProvider.generateToken(username);
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    /**
     * Logout: xoá refresh token và blacklist access token.
     * Gọi DELETE /api/auth/logout?refreshToken=xxx
     * với header Authorization: Bearer <accessToken>
     */
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "refreshToken", required = false) String refreshToken) {

        // Lấy chuỗi token (loại bỏ "Bearer ")
        String accessToken = authHeader.replaceFirst("^Bearer\\s+", "");

        // Gọi service để xử lý logout
        authService.logout(accessToken, refreshToken);

        // Trả về 204 No Content
        return ResponseEntity.noContent().build();
    }
}

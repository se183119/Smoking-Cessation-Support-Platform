package net.qsmoke.team.controller;

import net.qsmoke.team.dto.AuthResponse;
import net.qsmoke.team.dto.LoginRequest;
import net.qsmoke.team.dto.RegisterRequest;
import net.qsmoke.team.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import net.qsmoke.team.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
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
}

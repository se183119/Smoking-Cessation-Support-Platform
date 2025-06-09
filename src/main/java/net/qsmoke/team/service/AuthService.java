package net.qsmoke.team.service;

import jakarta.transaction.Transactional;
import net.qsmoke.team.dto.*;
import net.qsmoke.team.entity.RefreshToken;
import net.qsmoke.team.entity.Role;
import net.qsmoke.team.entity.User;
import net.qsmoke.team.service.TokenBlacklistService;
import net.qsmoke.team.repository.UserRepository;
import net.qsmoke.team.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;           // mới
    private final TokenBlacklistService tokenBlacklistService;       // mới

    public AuthResponse register(RegisterRequest request) {
        //email exits or not?
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.USER)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtTokenProvider.generateToken(String.valueOf(user)))
                .email(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(user.getRole())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return AuthResponse.builder()
                    .token(jwtTokenProvider.generateToken(String.valueOf(user)))
                    .email(user.getEmail())
                    .fullName(user.getFirstName() + " " + user.getLastName())
                    .role(user.getRole())
                    .build();

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email/password");
        }
    }

    /** Sinh access + refresh token mới từ refreshToken cũ */
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        // 1. Verify và lấy lại đối tượng RefreshToken
        RefreshToken rt = refreshTokenService.verify(refreshToken);

        // 2. Lấy user từ token
        User user = rt.getUser();

        // 3. Sinh access mới
        String newAccess = jwtTokenProvider.generateToken(user.getEmail());

        // 4. (tuỳ chọn) Có thể tái sử dụng hoặc sinh lại refresh
        //    Giữ nguyên refreshToken hoặc xoá->tạo mới:
        // String newRefresh = refreshTokenService.create(user);
        String newRefresh = refreshToken;

        return AuthResponse.builder()
                .token(newAccess)
                .refreshToken(newRefresh)
                .email(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(user.getRole())
                .build();
    }

    /** Logout: xóa refresh-token và blacklist access-token */
    @Transactional
    public void logout(String accessToken, String refreshToken) {
        // 1. Xóa refresh-token
        if (refreshToken != null) {
            refreshTokenService.delete(refreshToken);
        }
        // 2. Blacklist access-token
        Instant expiry = jwtTokenProvider.getExpiryFromToken(accessToken);
        tokenBlacklistService.blacklist(accessToken, expiry);
    }
}
package net.qsmoke.team.service;

import lombok.RequiredArgsConstructor;
import net.qsmoke.team.entity.RefreshToken;
import net.qsmoke.team.entity.User;
import net.qsmoke.team.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repo;

    /** TTL (ms) cho refresh token, cấu hình trong application.properties */
    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenDurationMs;

    /** Tạo mới, xóa token cũ của user, trả về chuỗi token */
    @Transactional
    public String create(User user) {
        repo.deleteAllByUser(user);
        RefreshToken rt = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
        repo.save(rt);
        return rt.getToken();
    }

    /** Xóa một refresh token */
    @Transactional
    public void delete(String token) {
        repo.deleteByToken(token);
    }

    /** Kiểm tra và trả lại RefreshToken nếu hợp lệ, ngược lại ném exception */
    public RefreshToken verify(String token) {
        RefreshToken rt = repo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (rt.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(rt);
            throw new RuntimeException("Refresh token expired");
        }
        return rt;
    }
}

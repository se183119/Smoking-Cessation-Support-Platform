package net.qsmoke.team.service;

import lombok.RequiredArgsConstructor;
import net.qsmoke.team.entity.BlacklistedToken;
import net.qsmoke.team.repository.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final BlacklistedTokenRepository repo;

    /** Lưu token vào blacklist với expiry timestamp */
    public void blacklist(String token, Instant expiry) {
        BlacklistedToken b = BlacklistedToken.builder()
                .token(token)
                .expiry(expiry)
                .build();
        repo.save(b);
    }

    /** Kiểm tra token có nằm trong blacklist và chưa hết hạn không */
    public boolean isBlacklisted(String token) {
        return repo.findByToken(token)
                .filter(b -> b.getExpiry().isAfter(Instant.now()))
                .isPresent();
    }
}

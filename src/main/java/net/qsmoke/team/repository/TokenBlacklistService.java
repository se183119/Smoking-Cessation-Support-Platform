package net.qsmoke.team.repository;

import java.time.Instant;

public interface TokenBlacklistService {
    void blacklist(String token, Instant expiry);
    boolean isBlacklisted(String token);
}

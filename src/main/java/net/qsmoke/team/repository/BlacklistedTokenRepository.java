package net.qsmoke.team.repository;

import net.qsmoke.team.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, String> {
    Optional<BlacklistedToken> findByToken(String token);
}

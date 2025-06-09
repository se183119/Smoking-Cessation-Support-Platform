package net.qsmoke.team.repository;

import net.qsmoke.team.entity.RefreshToken;
import net.qsmoke.team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteAllByUser(User user);
}

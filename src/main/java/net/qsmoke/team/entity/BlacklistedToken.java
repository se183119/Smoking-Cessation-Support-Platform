package net.qsmoke.team.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "blacklisted_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlacklistedToken {
    @Id
    private String token;

    @Column(nullable = false)
    private Instant expiry;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }
}


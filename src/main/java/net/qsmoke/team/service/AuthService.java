package net.qsmoke.team.service;

import net.qsmoke.team.dto.AuthResponse;
import net.qsmoke.team.dto.LoginRequest;
import net.qsmoke.team.dto.RegisterRequest;
import net.qsmoke.team.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.qsmoke.team.repository.UserRepository;
import net.qsmoke.team.security.JwtUtil;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.setPhone(req.getPhone());
        user.setRole("user");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

//    public AuthResponse login(LoginRequest req) {
//        User user = userRepository.findByEmail(req.getEmail())
//                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
//        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid email or password");
//        }
//        String token = jwtUtil.generateToken(user.getEmail());
//        return new AuthResponse(token);
//    }
public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Invalid email"));

    String token = jwtUtil.generateToken(user.getEmail());
    return new AuthResponse(token);
}

}

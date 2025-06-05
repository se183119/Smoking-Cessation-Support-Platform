package net.qsmoke.team.service;
//
//import net.qsmoke.team.dto.AuthResponse;
//import net.qsmoke.team.dto.LoginRequest;
//import net.qsmoke.team.dto.RegisterRequest;
//import net.qsmoke.team.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import net.qsmoke.team.repository.UserRepository;
//import net.qsmoke.team.security.JwtUtil;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
//public class AuthService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired private PasswordEncoder passwordEncoder;
//    @Autowired private JwtUtil jwtUtil;
//    private final AuthenticationManager authenticationManager;
//
//    public void register(RegisterRequest req) {
//        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already exists");
//        }
//        User user = new User();
//        user.setEmail(req.getEmail());
//        user.setPassword(passwordEncoder.encode(req.getPassword()));
////        user.setFullName(req.getFullName());
//        user.setPhone(req.getPhone());
//        user.setRole("user");
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());
//        userRepository.save(user);
//    }
//
////    public AuthResponse login(LoginRequest req) {
////        User user = userRepository.findByEmail(req.getEmail())
////                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
////        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
////            throw new RuntimeException("Invalid email or password");
////        }
////        String token = jwtUtil.generateToken(user.getEmail());
////        return new AuthResponse(token);
////    }
//public AuthResponse login(LoginRequest request) {
//    authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//    );
//
//    User user = userRepository.findByEmail(request.getEmail())
//            .orElseThrow(() -> new UsernameNotFoundException("Invalid email"));
//
//    String token = jwtUtil.generateToken(user.getEmail());
//    return new AuthResponse(token);
//}
//
//}

import net.qsmoke.team.dto.*;
import net.qsmoke.team.entity.Role;
import net.qsmoke.team.entity.User;
import net.qsmoke.team.repository.UserRepository;
import net.qsmoke.team.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

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
            // Authenticate user
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getEmail(),
//                            request.getPassword()
//                    )
//            );
//
//            // Get user details
//            User user = userRepository.findByEmail(request.getEmail())
//                    .orElseThrow(() -> new BadCredentialsException("User not found"));
//
//            // Generate token and build response
//            String token = jwtTokenProvider.generateToken(authentication);
//
//            return AuthResponse.builder()
//                    .token(token)
//                    .email(user.getEmail())
//                    .fullName(user.getFirstName() + " " + user.getLastName())
//                    .role(user.getRole())
//                    .build();

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email/password");
        }
    }
}
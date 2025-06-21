package com.swpteam.smokingcessation.feature.service.impl.identity;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.swpteam.smokingcessation.domain.dto.auth.request.*;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.AccountMapper;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.enums.AccountStatus;
import com.swpteam.smokingcessation.domain.enums.Role;
import com.swpteam.smokingcessation.domain.dto.auth.response.AuthenticationResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.GoogleTokenResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.IntrospectResponse;
import com.swpteam.smokingcessation.feature.integration.mail.MailService;
import com.swpteam.smokingcessation.domain.entity.Member;
import com.swpteam.smokingcessation.feature.repository.InvalidatedTokenRepository;
import com.swpteam.smokingcessation.feature.repository.MemberRepository;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.feature.repository.SettingRepository;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.InvalidatedToken;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.feature.service.interfaces.identity.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    AccountRepository accountRepository;
    MemberRepository memberRepository;
    SettingRepository settingRepository;
    AccountMapper accountMapper;
    InvalidatedTokenRepository invalidatedTokenRepository;
    MailService mailService;
    WebClient webClient = WebClient.create();

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.access-token-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refresh-token-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jtw.password-reset-token-duration}")
    protected long RESET_DURATION;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri:http://localhost:8080/oauth2/callback}")
    protected String REDIRECT_URI;

    @NonFinal
    @Value("${app.frontend-domain}")
    protected String FRONTEND_DOMAIN;


    @Override
    public GoogleTokenResponse getGoogleToken(GoogleTokenRequest request) {
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        Mono<GoogleTokenResponse> responseMono = webClient.post()
                .uri(tokenEndpoint)
                .bodyValue(
                        "code=" + request.getCode() +
                                "&client_id=" + CLIENT_ID +
                                "&client_secret=" + CLIENT_SECRET +
                                "&redirect_uri=" + REDIRECT_URI +
                                "&grant_type=authorization_code"
                )
                .header("Content-Type", "application/x-www-form-urlencoded")
                .retrieve()
                .bodyToMono(GoogleTokenResponse.class);

        return responseMono.block();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var account = accountRepository.findByEmailAndIsDeletedFalse(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        var accessToken = generateAccessToken(account);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .authenticated(true)
                .build();
    }

    private String generateAccessToken(Account account) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(account.getEmail())
                .issuer("swpteam")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .claim("scope", buildScope(account))
                .build();


        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }

    }

    private String generateResetToken(Account account) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())//unique ID to invalidate later on
                .subject(account.getEmail())
                .issuer("swpteam")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(RESET_DURATION, ChronoUnit.SECONDS).toEpochMilli() // 30 days validity
                ))
                .claim("type", "reset")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create reset token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (account.getRole() != null) {
            stringJoiner.add("ROLE_" + account.getRole().name());
        }

        return stringJoiner.toString();
    }

    @Override
    public AuthenticationResponse refreshToken(String token) throws ParseException, JOSEException {
        var signedJWT = verifyToken(token, true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var email = signedJWT.getJWTClaimsSet().getSubject();

        Account account = accountRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        var newToken = generateAccessToken(account);

        return AuthenticationResponse.builder()
                .accessToken(newToken)
                .authenticated(true)
                .build();
    }

    @Override
    public AccountResponse register(RegisterRequest request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        }

        Account account = accountMapper.toEntityFromRegister(request);

        account.setRole(Role.MEMBER);
        account.setStatus(AccountStatus.ACTIVE);

        settingRepository.save(Setting.getDefaultSetting(account));
        memberRepository.save(Member.getDefaultMember(account));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return accountMapper.toResponse(accountRepository.save(account));
    }

//    @Override
//    public AccountResponse register(RegisterRequest request) {
//        if (accountRepository.existsByEmail(request.getEmail())) {
//            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
//        }
//
//        Account account = accountMapper.toEntityFromRegister(request);
//
//        account.setRole(Role.MEMBER);
//        account.setStatus(AccountStatus.ACTIVE);
//
//        // ✅ Đặt password trước khi lưu account
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        account.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        // ✅ Lưu account trước để có ID
//        Account savedAccount = accountRepository.save(account);
//
//        Setting setting = Setting.getDefaultSetting(savedAccount);
//        setting.setAccount(account);
//        settingRepository.save(setting);
//
//
//        // ✅ Sau đó mới tạo setting và member
//        settingRepository.save(Setting.getDefaultSetting(savedAccount));
//        memberRepository.save(Member.getDefaultMember(savedAccount));
//
//        return accountMapper.toResponse(savedAccount);
//    }


    @Override
    public IntrospectResponse introspect(TokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.USED_TOKEN);

        return signedJWT;
    }

    @Override
    public void sendResetPasswordEmail(String email) {
        Account account = accountRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        //generate reset token
        String token = generateResetToken(account);
        String resetLink = FRONTEND_DOMAIN + "/reset-password?token=" + token;

        try {
            mailService.sendResetPasswordEmail(email, resetLink, account.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send reset password email to {}", email, e);
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        String emailFromToken;
        String jwtId;

        try {
            JWSObject jwsObject = JWSObject.parse(request.getToken());
            JWTClaimsSet jwtClaimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            if (!jwtClaimsSet.getClaim("type").equals("reset")) {
                throw new AppException(ErrorCode.INVALID_RESET_TOKEN);
            }
            if (jwtClaimsSet.getExpirationTime().before(new Date()) || invalidatedTokenRepository.existsById(jwtClaimsSet.getJWTID())) {
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }
            emailFromToken = jwtClaimsSet.getSubject();
            jwtId = jwtClaimsSet.getJWTID();
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_RESET_TOKEN);
        }

        Account account = accountRepository.findByEmailAndIsDeletedFalse(emailFromToken).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);

        if (jwtId != null) {
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtId)
                    .expiryTime(null)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        }
    }

    @Override
    public void logout(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(token, true);

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if (jwtId != null) {
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtId)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        }
    }
}

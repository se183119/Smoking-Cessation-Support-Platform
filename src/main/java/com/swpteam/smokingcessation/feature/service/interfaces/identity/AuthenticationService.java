package com.swpteam.smokingcessation.feature.service.interfaces.identity;

import com.nimbusds.jose.JOSEException;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.dto.auth.request.*;
import com.swpteam.smokingcessation.domain.dto.auth.response.AuthenticationResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.GoogleTokenResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    public GoogleTokenResponse getGoogleToken(GoogleTokenRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public AuthenticationResponse refreshToken(String token) throws ParseException, JOSEException;

    public AccountResponse register(RegisterRequest request);

    public IntrospectResponse introspect(TokenRequest request) throws JOSEException, ParseException;

    public void sendResetPasswordEmail(String email);

    public void resetPassword(ResetPasswordRequest request);

    public void logout(String token) throws ParseException, JOSEException;
}

package com.swpteam.smokingcessation.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.constant.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        String message = errorCode.getMessage();

        String causeMsg = null;
        if (authException.getCause() != null && authException.getCause().getMessage() != null) {
            causeMsg = authException.getCause().getMessage();
        } else if (authException.getMessage() != null) {
            causeMsg = authException.getMessage();
        }

        // check for expired tokens
        if (causeMsg != null && causeMsg.toLowerCase().contains("expired")) {
            errorCode = ErrorCode.TOKEN_EXPIRED;
            message = errorCode.getMessage();
        }

        response.setStatus(errorCode.getHttpCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}

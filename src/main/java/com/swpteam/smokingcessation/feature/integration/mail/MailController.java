package com.swpteam.smokingcessation.feature.integration.mail;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.constant.SuccessCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailController {

    MailService mailService;

    @PostMapping("/send-template")
    public ResponseEntity<ApiResponse<Void>> sendMailTemplate(@RequestBody MailRequest request) {
        mailService.sendPaymentSuccessEmail(request.getAccountId(), request.getSubscriptionId(), request.getAmount());

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.SEND_MAIL_SUCCESS.getCode())
                        .message(SuccessCode.SEND_MAIL_SUCCESS.getMessage())
                        .build()
        );
    }
}

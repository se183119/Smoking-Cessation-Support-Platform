package com.swpteam.smokingcessation.feature.integration.mail;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.entity.Message;
import com.swpteam.smokingcessation.domain.entity.Subscription;
import com.swpteam.smokingcessation.feature.repository.SubscriptionRepository;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.exception.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailService {

    JavaMailSender mailSender;
    SpringTemplateEngine templateEngine;

    AccountRepository accountRepository;
    SubscriptionRepository subscriptionRepository;

    @NonFinal
    @Value("${spring.mail.username}")
    String hostEmail;

    public void sendPaymentSuccessEmail(String accountId, String subscriptionId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Subscription subscription = subscriptionRepository.findByIdAndIsDeletedFalse(subscriptionId)
                .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_FOUND));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            Context context = new Context();
            context.setVariable("username", account.getUsername() != null ? account.getUsername() : "User");
            context.setVariable("amount", String.format("%.2f", amount / 100.0));
            context.setVariable("startDate", subscription.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            context.setVariable("endDate", subscription.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            String html = templateEngine.process("payment-success", context);

            helper.setTo(account.getEmail());
            helper.setSubject("Payment Successful - Subscription ID " + subscription.getId());
            helper.setText(html, true);
            helper.setFrom(hostEmail);

            mailSender.send(message);

        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void sendMotivationMail(String to, Message message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            String content = message.getContent();
            context.setVariable("quote", content);
            context.setVariable("sendTime", LocalDateTime.now());

            String htmlContent = templateEngine.process("motivation-template", context);
            helper.setText(htmlContent, true); // true chỉ định nội dung là HTML
            helper.setSubject("💪 Daily Motivation");
            helper.setTo(to);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void sendReminderMail(String to) throws MessagingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("deadline", LocalDateTime.now().plusMinutes(30));
            context.setVariable("sendTime", LocalDateTime.now());
            String htmlContent = templateEngine.process("reminder-template", context);

            helper.setText(htmlContent, true);
            helper.setSubject("⏰ Friendly Reminder");
            helper.setTo(to);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void sendResetPasswordEmail(String to, String resetLink, String userName) throws MessagingException {
        // Prepare the Thymeleaf context
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("resetLink", resetLink);
        context.setVariable("expirationTime", "15 minutes");

        // Generate the email content using Thymeleaf
        String htmlContent = templateEngine.process("reset-mail-template", context);

        // Create and send the email
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject("Password Reset Request");
        helper.setText(htmlContent, true); // true indicates HTML content

        mailSender.send(mimeMessage);
    }
}




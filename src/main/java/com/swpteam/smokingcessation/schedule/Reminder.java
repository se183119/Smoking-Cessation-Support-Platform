package com.swpteam.smokingcessation.schedule;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.feature.integration.mail.MailService;
import com.swpteam.smokingcessation.domain.entity.Message;
import com.swpteam.smokingcessation.feature.repository.MessageRepository;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.feature.repository.SettingRepository;
import com.swpteam.smokingcessation.domain.enums.MotivationFrequency;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Reminder {

    MessageRepository messageRepository;
    MailService mailService;
    SettingRepository settingRepository;
    Random random = new Random();

    @Scheduled(cron = "0 * * * * *")
    public void sendReminders() {
//        LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0);
//        LocalTime deadlineIn30Minutes = currentTime.plusMinutes(30);
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        LocalTime in30 = now.plusMinutes(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String nowStr = now.format(formatter);
        String in30Str = in30.format(formatter);

//        List<Setting> settings = settingRepository.findByReportDeadlineAndIsDeletedFalse(deadlineIn30Minutes);
        List<Setting> settings = settingRepository.findAllByReportDeadlineBetween(nowStr, in30Str);

        for (Setting setting : settings) {
            try {
                Account account = setting.getAccount();
                String userEmail = account.getEmail();

                log.info("Sending reminder to user with email: {}", userEmail);

                mailService.sendReminderMail(userEmail);
            } catch (Exception e) {
                log.error("Failed to send reminder for setting accountId: {}", setting.getAccount().getId(), e);
            }
        }
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyMotivation() {
        sendMotivation(MotivationFrequency.DAILY);
    }

    @Scheduled(cron = "0 0 8,14,20,0 * * *")
    public void sendEvery6HoursMotivation() {
        sendMotivation(MotivationFrequency.EVERY6HOURS);
    }

    @Scheduled(cron = "0 0 8,20 * * *")
    public void sendEvery12HoursMotivation() {
        sendMotivation(MotivationFrequency.EVERY12HOURS);
    }

    @Scheduled(cron = "0 0 8 * * SUN")
    public void sendWeeklyMotivation() {
        sendMotivation(MotivationFrequency.WEEKLY);
    }

    @Scheduled(cron = "0 0 8 1 * *")
    public void sendMonthlyMotivation() {
        sendMotivation(MotivationFrequency.MONTHLY);
    }

    private Message getRandomMotivationMessage() {
        List<Message> motivationMessages = messageRepository.findAllByIsDeletedFalse();

        int randomIndex = random.nextInt(motivationMessages.size());

        return motivationMessages.get(randomIndex);
    }

    private void sendMotivation(MotivationFrequency motivationFrequency) {
        log.info("Sending motivation with frequency: {}", motivationFrequency);

        List<Setting> dailySettings = settingRepository.findByMotivationFrequency(motivationFrequency);
        if (dailySettings.isEmpty()) {
            log.info("No users found with {} motivation setting", motivationFrequency);
            return;
        }

        log.info("Sending motivation messages to {} user(s)", dailySettings.size());
        for (Setting setting : dailySettings) {
            String email = setting.getAccount().getEmail();
            Message randomMotivationMessage = getRandomMotivationMessage();
            try {
                mailService.sendMotivationMail(email, randomMotivationMessage);
            } catch (Exception e) {
                log.error("Failed to send motivation to email: {}", email, e);
            }
        }
    }
}
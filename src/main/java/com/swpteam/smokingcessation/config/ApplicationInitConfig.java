package com.swpteam.smokingcessation.config;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.enums.Role;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.feature.repository.SettingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    @NonFinal
    @Value("${admin.email}")
    String adminEmail;

    @NonFinal
    @Value("${admin.password}")
    String defaultPassword;

    PasswordEncoder passwordEncoder;

    SettingRepository settingRepository;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mssql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(AccountRepository accountRepository) {
        log.info("Initializing application ...");

        return args -> {
            if (accountRepository.findByEmail(adminEmail).isEmpty()) {
                Account account = Account.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(defaultPassword))
                        .role(Role.ADMIN)
                        .build();


                Setting setting = Setting.getDefaultSetting(account);

                account.setSetting(setting);

                accountRepository.save(account);
                settingRepository.save(setting);

                log.info("An admin account has been created with email: {}, default password: {}. Please change the password immediately.", adminEmail, defaultPassword);
            }

            log.info("Application initialization completed ...");
        };
    }
}

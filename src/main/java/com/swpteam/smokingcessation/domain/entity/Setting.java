package com.swpteam.smokingcessation.domain.entity;

import com.swpteam.smokingcessation.domain.enums.Language;
import com.swpteam.smokingcessation.domain.enums.MotivationFrequency;
import com.swpteam.smokingcessation.domain.enums.Theme;
import com.swpteam.smokingcessation.domain.enums.TrackingMode;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Setting extends BaseEntity {

    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "account_id")
    Account account;

    @Enumerated(EnumType.STRING)
    Theme theme;

    @Enumerated(EnumType.STRING)
    Language language;

    @Enumerated(EnumType.STRING)
    TrackingMode trackingMode;

    @Enumerated(EnumType.STRING)
    MotivationFrequency motivationFrequency;

    LocalTime reportDeadline;

    public static Setting getDefaultSetting(Account account) {
        return Setting.builder()
                .id(account.getId())
                .account(account)
                .theme(Theme.LIGHT)
                .language(Language.EN)
                .motivationFrequency(MotivationFrequency.NEVER)
                .trackingMode(TrackingMode.AUTO_COUNTER)
                .reportDeadline(LocalTime.of(22, 0))
                .build();
    }
}

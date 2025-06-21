package com.swpteam.smokingcessation.domain.entity;

import com.swpteam.smokingcessation.domain.enums.Currency;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Membership extends BaseEntity {

    String name;

    int durationDays;
    double price;

    @Enumerated(EnumType.STRING)
    Currency currency;

    String description;

    @OneToMany(mappedBy = "membership")
    List<Subscription> subscriptions;
}

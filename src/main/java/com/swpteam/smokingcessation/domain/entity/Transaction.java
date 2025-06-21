package com.swpteam.smokingcessation.domain.entity;

import com.swpteam.smokingcessation.domain.enums.TransactionStatus;
import com.swpteam.smokingcessation.domain.enums.TransactionType;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    Account account;

    double amount;

    @Enumerated(EnumType.STRING)
    TransactionStatus status;

    @Enumerated(EnumType.STRING)
    TransactionType type;

    public static Transaction startTransaction(Account account) {
        return Transaction.builder()
                .account(account)
                .status(TransactionStatus.PENDING)
                .build();
    }
}

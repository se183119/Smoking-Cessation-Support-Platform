package com.swpteam.smokingcessation.domain.dto.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponse {

    private String id;
    private String accountId;
    private int cigarettesSmoked;
    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
package com.swpteam.smokingcessation.domain.dto.message;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {

    String id;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

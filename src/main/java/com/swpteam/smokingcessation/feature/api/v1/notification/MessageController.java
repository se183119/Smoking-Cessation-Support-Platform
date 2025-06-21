package com.swpteam.smokingcessation.feature.api.v1.notification;

import com.swpteam.smokingcessation.domain.dto.message.MessageRequest;
import com.swpteam.smokingcessation.domain.dto.message.MessageResponse;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.feature.service.interfaces.notification.MessageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {

    MessageService messageService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<MessageResponse>>> getMessagePage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<MessageResponse>>builder()
                        .code(SuccessCode.MEMBERSHIP_GET_ALL.getCode())
                        .message(SuccessCode.MEMBERSHIP_GET_ALL.getMessage())
                        .result(messageService.getMessagePage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<MessageResponse>> getMessageById(@PathVariable String id) {
        MessageResponse response = messageService.getById(id);
        return ResponseEntity.ok(
                ApiResponse.<MessageResponse>builder()
                        .code(SuccessCode.MESSAGE_GET_BY_ID.getCode())
                        .message(SuccessCode.MESSAGE_GET_BY_ID.getMessage())
                        .result(response)
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<MessageResponse>> createMessage(@Valid @RequestBody MessageRequest request) {
        MessageResponse response = messageService.createMessage(request);
        return ResponseEntity.ok(
                ApiResponse.<MessageResponse>builder()
                        .code(SuccessCode.MESSAGE_CREATED.getCode())
                        .message(SuccessCode.MESSAGE_CREATED.getMessage())
                        .result(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<MessageResponse>> updateMessage(@PathVariable String id, @Valid @RequestBody MessageRequest request) {
        MessageResponse response = messageService.updateMessage(id, request);
        return ResponseEntity.ok(
                ApiResponse.<MessageResponse>builder()
                        .code(SuccessCode.MESSAGE_UPDATED.getCode())
                        .message(SuccessCode.MESSAGE_UPDATED.getMessage())
                        .result(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable String id) {
        messageService.softDeleteMessageById(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.MESSAGE_DELETED.getCode())
                        .message(SuccessCode.MEMBERSHIP_DELETED.getMessage())
                        .build()
        );
    }
}
